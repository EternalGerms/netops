package com.netops.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotBlank String login,
        @NotBlank String senha,
        @NotNull String role // Ex: USER, ADMIN
) {
}