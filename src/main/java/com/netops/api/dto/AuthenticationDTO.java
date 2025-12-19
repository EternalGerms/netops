package com.netops.api.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank String login,
        @NotBlank String senha
) {
}