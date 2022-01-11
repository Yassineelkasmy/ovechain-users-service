package com.users.service.services;

import com.users.service.dto.CreatePropertyDto;
import com.users.service.entity.Property;
import com.users.service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class PropertyService {
    @Autowired
    StorageService storageService;

    @Autowired
    PropertyRepository propertyRepository;

    public Property[] getUserNewProperties(String uid) {
        Property property = new Property();
        property.setUserId(uid);
        return this.propertyRepository.findAll(Example.of(property)).toArray(Property[]::new);
    }


    public Property registerProperty(CreatePropertyDto createPropertyDto, String uid) throws IOException, ExecutionException, InterruptedException {
        this.storageService.downloadPropertyFolder(uid,createPropertyDto.getCode(), 0);
        Property property = new Property();
        property.setUserId(uid);
        property.setCode(createPropertyDto.getCode());
        boolean propertyExists = this.propertyRepository.exists(Example.of(property));
        if(propertyExists) {
            return this.propertyRepository.findOne(Example.of(property)).get();
        }
        Property newProperty = new Property(
                UUID.randomUUID().toString(),
                uid,
                createPropertyDto.getCode(),
                createPropertyDto.getTitle(),
                createPropertyDto.getDescription(),
                createPropertyDto.getAddress(),
                null,
                false
        );

        return this.propertyRepository.save(newProperty);
    }
}
