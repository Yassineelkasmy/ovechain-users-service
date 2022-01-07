package com.users.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserDto {

    @NotBlank(message = "First name required")
    private String firstName;

    @NotBlank()
    private String lastName;

    @NotBlank()
    private String phone;

    @NotBlank()
    private String address;
}
