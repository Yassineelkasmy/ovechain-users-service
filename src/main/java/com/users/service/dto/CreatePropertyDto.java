package com.users.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreatePropertyDto {
    @NotBlank
    private String code;

    @NotBlank
    private String title;
    @NotBlank
    private String description;

    @NotBlank
    private String address;

    @NotNull
    private Integer optionals;
}
