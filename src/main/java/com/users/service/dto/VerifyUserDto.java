package com.users.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VerifyUserDto {
    @NotBlank
    String userId;
}
