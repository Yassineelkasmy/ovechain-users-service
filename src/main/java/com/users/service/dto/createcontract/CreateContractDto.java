package com.users.service.dto.createcontract;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateContractDto {
    @NotBlank
    private String wallet;

    @NotNull
    private Float price;

    @NotBlank
    private String propertyCode;

    String[] whiteListWallets;
    String[] blackListWallets;

}
