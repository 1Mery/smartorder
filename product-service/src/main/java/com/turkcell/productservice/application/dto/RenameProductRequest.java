package com.turkcell.productservice.application.dto;

import jakarta.validation.constraints.NotBlank;

public record RenameProductRequest(
        @NotBlank(message = "New name cannot be blank")
        String newName
) {
}
