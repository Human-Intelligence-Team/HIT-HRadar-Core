package org.hit.hradar.domain.competencyReport.gemini.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.hit.hradar.domain.competencyReport.command.application.dto.PersonalCompetencySourceDTO;
import org.hit.hradar.domain.competencyReport.gemini.dto.OutputResultDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentRowDTO;
import org.hit.hradar.domain.competencyReport.query.dto.request.ContentSearchRequest;
import org.hit.hradar.domain.competencyReport.query.mapper.ContentMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

        private final ChatClient chatClient;
        private final ContentMapper contentMapper;
        private final ObjectMapper objectMapper;

        public GeminiService(ChatClient.Builder builder, ContentMapper contentMapper, ObjectMapper objectMapper) {
                this.chatClient = builder.build();
                this.contentMapper = contentMapper;
                this.objectMapper = objectMapper;
        }

  public OutputResultDTO getGeminiData(PersonalCompetencySourceDTO dto) {
    // 1. 콘텐츠 데이터 최적화 (기존 로직 유지하되 간결하게)

    ContentSearchRequest contentRequest = new ContentSearchRequest(dto.getComId());
    List<ContentRowDTO> contentAllRows = contentMapper.findAllContents(contentRequest);

    List<Map<String, Object>> optimizedContents = contentAllRows.stream()
        .collect(Collectors.groupingBy(ContentRowDTO::getContentId))
        .values().stream()
        .map(rows -> {
          ContentRowDTO first = rows.get(0);
          Map<String, Object> map = new HashMap<>();
          map.put("contentId", first.getContentId());
          map.put("title", first.getTitle());
          map.put("tags", rows.stream().map(ContentRowDTO::getTagName).filter(Objects::nonNull).distinct().toList());
          map.put("level", first.getLevelName());
          map.put("type", first.getTypeName());
          return map;
        }).toList();

    try {
      String contentsJson = objectMapper.writeValueAsString(optimizedContents);
      String employeeJson = objectMapper.writeValueAsString(dto);

      // 2. 프롬프트 템플릿 정의 (가독성과 이스케이프 최소화)
      String promptTemplate = """
                {instruction}
                
                [분석 대상 사원 정보]
                {employeeData}
                
                [가용 학습 콘텐츠 목록]
                {contentList}
                
                위 데이터를 바탕으로 성과를 분석하고 최적의 학습 콘텐츠를 추천하여 JSON으로 응답해줘.
                """;

      String instruction = String.format("""
                너는 20년 경력의 전문 HR 컨설턴트야. [%s] 부서의 [%s] 직급 사원을 분석 중이야.
                응답은 반드시 아래 JSON 형식을 엄격히 지켜줘:
                {
                  "ownerId": %d,
                  "cycleId": %d,
                  "kpiOkrResultSummary": "성과 요약 내용",
                  "goalFailureAnalysis": "미달성 역량 분석 내용",
                  "contentRow": [
                    { "contentId": 1, "reason": "추천 사유" }
                  ]
                }
                """, dto.getDepartmentName(), dto.getPositionName(), dto.getOwnerId(), dto.getCycleId());

      // 3. AI 호출
      return chatClient.prompt()
          .user(u -> u.text(promptTemplate)
              .param("instruction", instruction)
              .param("employeeData", employeeJson)
              .param("contentList", contentsJson))
          .call()
          .entity(OutputResultDTO.class);

    } catch (JsonProcessingException e) {
      throw new RuntimeException("AI 분석을 위한 데이터 변환 중 오류 발생", e);
    }
  }

}
