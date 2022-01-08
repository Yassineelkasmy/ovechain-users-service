package com.users.service.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "users")
@Data
@AllArgsConstructor
public class User implements Serializable {


    private static final long serialVersionUID = 4408418647685225829L;
    @Id
    private String uid;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private boolean isVerified;


}