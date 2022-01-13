package com.users.service.controller;


import com.users.service.dto.VerifyUserDto;
import com.users.service.entity.Property;
import com.users.service.entity.User;
import com.users.service.services.PropertyService;
import com.users.service.services.StorageService;
import com.users.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    ServletContext context;

    @Autowired
    StorageService storageService;

    @GetMapping("/users")
    ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.userService.getUsers());
    }

    @GetMapping("/properties")
    ResponseEntity<List<Property>> getProperties() {
        return ResponseEntity.ok(this.propertyService.getProperties());
    }

    @PostMapping("/verifyuser")
    ResponseEntity<User> verifyUser(@Valid @RequestBody VerifyUserDto verifyUserDto) {
        User user = this.userService.verifyUser(verifyUserDto.getUserId());
        return ResponseEntity.ok(user);
    }



    @RequestMapping("/userfolder/{fileName:.+}")
    public void downloadUserFolder(HttpServletRequest request, HttpServletResponse response,
                                   @PathVariable("fileName") String fileName) throws Exception {

        String uid = fileName.substring(0,fileName.lastIndexOf("."));
        this.storageService.zipUserFolder(uid);

        System.out.println("Downloading file :- " + fileName);

        String downloadFolder = context.getRealPath("./static/");
        Path file = Paths.get("./static", fileName);
        // Check if file exists
        if (Files.exists(file)) {
            // set content type
            response.setContentType("application/pdf");
            // add response header
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            try {
                // copies all bytes from a file to an output stream
                Files.copy(file, response.getOutputStream());
                // flushes output stream
                response.getOutputStream().flush();
            } catch (IOException e) {
                System.out.println("Error :- " + e.getMessage());
            }
        } else {
            System.out.println("Sorry File not found!!!!");
        }
    }
}
