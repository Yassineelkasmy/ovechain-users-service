package com.users.service.dto;

import lombok.Data;

@Data
public class CreatePropertyDto {
    private String code;
    private String title;
    private String description;
    private String address;
    private Integer optionals;
}
