package com.users.service.controller;

import com.users.service.auth.models.AuthUser;
import com.users.service.dto.CreatePropertyDto;
import com.users.service.dto.CreateUserDto;
import com.users.service.entity.Property;
import com.users.service.entity.User;
import com.users.service.services.PropertyService;
import com.users.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    PropertyService propertyService;


/*
    @GetMapping()
    public ResponseEntity<Property> getUser(@AuthenticationPrincipal AuthUser athUser) {
        try{
            User user = this.userService.getUser(athUser.getUid());
            return ResponseEntity.ok(user);

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
*/

    @PostMapping()
    public ResponseEntity<Property> registerProperty(@Valid @RequestBody CreatePropertyDto createUserDto, @AuthenticationPrincipal AuthUser athUser) throws IOException, ExecutionException, InterruptedException {
        try {
            Property property = this.propertyService.registerProperty(createUserDto, athUser.getUid());
            return ResponseEntity.ok(property);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
