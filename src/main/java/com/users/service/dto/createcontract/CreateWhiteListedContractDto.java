package com.users.service.dto.createcontract;


import com.users.service.enums.SmartContractType;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateWhiteListedContractDto extends CreateContractDto {

    SmartContractType type = SmartContractType.WHITELISTED;

    @NotEmpty
    String[] whiteListWallets;
}

