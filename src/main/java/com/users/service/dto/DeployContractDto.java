package com.users.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeployContractDto {
    @NotBlank
    String contractId;
    @NotBlank
    String contractAddress;
}
