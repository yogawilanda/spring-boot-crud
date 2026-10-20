package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @NotBlank @Size(max = 120) String name,
    @NotBlank @Size(max = 140) String slug,
    @Size(max = 5000) String description
) {}
