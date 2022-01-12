package com.users.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;

@Document(collection = "admins")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Admin {

    @Id
    String admin;
    String username;
    String password;

    Collection<Role> roles = new ArrayList<>();
}
