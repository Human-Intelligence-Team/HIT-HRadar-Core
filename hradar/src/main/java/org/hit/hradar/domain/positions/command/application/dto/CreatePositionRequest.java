package org.hit.hradar.domain.positions.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePositionRequest {
    @NotBlank @Size(max = 50)
    private String name;
    @NotNull
    private Integer rank;
}
