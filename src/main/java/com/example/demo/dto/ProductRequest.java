package com.example.demo.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductRequest(
    @NotBlank @Size(max = 180) String name,
    @NotBlank @Size(max = 200) String slug,
    @Size(max = 10000) String description,
    @NotNull @DecimalMin("0.00") BigDecimal price,
    @NotBlank @Pattern(regexp = "draft|published|sold") String status,
    @NotNull Long categoryId
) {}
