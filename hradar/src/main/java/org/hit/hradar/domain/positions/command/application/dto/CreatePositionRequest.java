package org.hit.hradar.domain.positions.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePositionRequest {
    @NotBlank
    private String name;
    @NotNull
    private Integer rank;
}
