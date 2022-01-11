package com.users.service.dto.createcontract;


        import com.users.service.enums.SmartContractType;
        import lombok.Data;
        import javax.validation.constraints.NotEmpty;

@Data
public class CreateBlackListedContractDto extends CreateContractDto {

    SmartContractType type = SmartContractType.BLACKLISTED;

    @NotEmpty
    String[] blackListWallets;
}

