package com.users.service.repository;

import com.users.service.entity.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends MongoRepository<Property,String> {

    public Property findPropertyById(String id);
}
