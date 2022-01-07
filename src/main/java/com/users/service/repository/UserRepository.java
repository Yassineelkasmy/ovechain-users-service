package com.users.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.users.service.entity.User;

public interface UserRepository extends MongoRepository<User, String> {}
