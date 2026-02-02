package org.hit.hradar.domain.competencyReport.gemini.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.PersonalCompetencySourceDTO;
import org.hit.hradar.domain.competencyReport.gemini.dto.OutputResultDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentRowDTO;
import org.hit.hradar.domain.competencyReport.query.mapper.ContentMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

  private final ChatClient chatClient;
  private final ContentMapper contentMapper;

  public GeminiService(ChatClient.Builder builder,  ContentMapper contentMapper) {
    this.chatClient = builder.build();
    this.contentMapper = contentMapper;
  }

  public OutputResultDTO getGeminiData(PersonalCompetencySourceDTO dto){

    List<ContentRowDTO> contentAll = contentMapper.findAllContents(null);

    // 1. 전문가 페르소나와 분석 환경 설정
    String systemInstruction = String.format(
        "너는 20년 경력의 전문 HR 컨설턴트야. 이번 분석 대상은 [%s] 부서의 [%s] 직급 사원이야.\n" +
            "해당 직급의 기대 역할(실무 숙련도 또는 리더십 등)을 고려하여 데이터를 심층 분석해줘.\n\n",
        // deptName, positionName
        dto.getDepartmentName()
        , dto.getPositionName()
    );

    // 2. 분석 지시사항 (미달성 원인 및 태그 매칭 포함)
    String analysisInstruction =
        "분석 원칙:\n" +
            "1. 성과 요약: PersonalCompetencySourceDTO kpiList는 kpi 데이터이고 PersonalCompetencySourceDTO안에 okrList는 okr 데이터 기반하여 KPI/OKR 달성률을 객관적으로 평가할 것.\n" +
            "2. 역량 분석: 미달성 목표가 있다면, [%s] 직급에 필요한 역량 중 무엇이 부족한지 분석할 것.\n" +
            "3. 맞춤형 추천: 제공된 contentAll의 tagName를 분석하여, 사원의 부족한 역량을 보완할 수 있는 콘텐츠를  contentAll 기반으로 컨텐츠를 최대 5개 추천할 것.\n\n";

    // 3. 최종 프롬프트 구성
    String prompt = String.format(
        "%s\n%s\n" +
            "[분석할 사원 데이터]\n%s\n\n" +
            "[우리 회사 학습 콘텐츠 목록]\n%s\n\n" +
            "위 정보를 바탕으로 OutputResultDTO 형식에 맞춰 리포트를 생성해줘.",


        systemInstruction,
        String.format(analysisInstruction, dto.getPositionName()),
        dto,
        contentAll.toString()
    );

    // 3. AI 호출 및 결과 반환
    return chatClient.prompt()
        .user(prompt)
        .call()
        .entity(OutputResultDTO.class);
  }

}
