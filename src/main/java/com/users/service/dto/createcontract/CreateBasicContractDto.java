package com.users.service.dto.createcontract;
import com.users.service.enums.SmartContractType;
import lombok.Data;


@Data
public class CreateBasicContractDto extends CreateContractDto{
    SmartContractType type = SmartContractType.BASIC;
}
