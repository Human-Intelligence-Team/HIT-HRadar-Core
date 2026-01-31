package org.hit.hradar.domain.competencyReport.gemini;

import org.hit.hradar.domain.competencyReport.gemini.Outfit.dto.OutfitPromptText;
import org.springframework.stereotype.Component;

@Component
public class PromptGenerator {
 /* public OutfitPromptText generate(OutfitPromptData data) {
    String prompt = """
                현재 날씨 정보
                - 기온: %.1f℃
                - 강수량: %.1fmm
                - 습도: %d%%
                - 강수 형태: %s
                - 풍속: %.1fm/s
                이 날씨에 맞는 옷차림 추천해 줘"""
        .formatted(
            data.getTemperature(),
            data.getRainfall(),
            data.getHumidity(),
            data.getPrecipitationType().getDescription(),
            data.getWindSpeed()
        );

    return new OutfitPromptText(prompt);
  }*/
}
