package org.hit.hradar.domain.competencyReport.gemini;

import org.hit.hradar.domain.competencyReport.gemini.Outfit.dto.OutfitPromptResponse;
import org.hit.hradar.domain.competencyReport.gemini.Outfit.dto.OutfitPromptText;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class AiCallService {
  private final OpenAiChatModel openAiChatModel;

  public AiCallService(OpenAiChatModel openAiChatModel) {
    this.openAiChatModel = openAiChatModel;
  }

  public OutfitPromptResponse generateResponse(OutfitPromptText promptText) {
    String responseText = openAiChatModel.call(promptText.getText());
    return new OutfitPromptResponse(responseText);
  }
}