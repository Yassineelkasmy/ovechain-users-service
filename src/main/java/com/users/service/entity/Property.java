package com.users.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "properties")
@Data
@AllArgsConstructor
public class Property implements Serializable{

    private static final long serialVersionUID = 4408418647685225829L;

    @Id
    private String id;
    private String userId;
    private String code;
    private String title;
    private String description;
    private String address;
    private boolean isVerified;
}
