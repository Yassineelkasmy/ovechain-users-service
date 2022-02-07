package com.users.service.entity;

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
public class DeployedContract implements Serializable{

    private static final long serialVersionUID = 4408418647685225829L;

    @Id
    private String id;
    public String propertyCode;
    public String contractAddress;
    public String type;
    public String sellerWallet;
    public Float price;
    public String title;
    public String description;
    public String propertyAddress;
}
