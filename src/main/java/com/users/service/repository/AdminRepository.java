package com.users.service.repository;

import com.users.service.entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {

    Admin findByUsername(String username);
}
