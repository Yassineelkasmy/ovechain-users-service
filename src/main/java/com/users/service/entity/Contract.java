package com.users.service.entity;

import com.users.service.enums.SmartContractType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "contracts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract implements Serializable{

    private static final long serialVersionUID = 4408418647685225829L;

    @Id
    private String id;
    private String userId;
    private String propertyId;
    private String sellerWallet;
    private Float priceETH;
    private Boolean isVerified;
    private SmartContractType type;
    private String[] whiteListWallets;
    private String[] blackListWallets;
}
