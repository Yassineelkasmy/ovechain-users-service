package com.users.service.controller;


import com.users.service.dto.DownloadPropertyFolderDto;
import com.users.service.dto.VerifyContractDto;
import com.users.service.dto.VerifyPropertyDto;
import com.users.service.dto.VerifyUserDto;
import com.users.service.entity.Contract;
import com.users.service.entity.Property;
import com.users.service.entity.User;
import com.users.service.services.ContractService;
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
    ContractService contractService;

    @Autowired
    ServletContext context;

    @Autowired
    StorageService storageService;

    // Users
    @GetMapping("/users")
    ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.userService.getUsers());
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

        Path file = Paths.get("./static", fileName);
        sendFile(response, fileName, file);
    }


    //Properties
    @GetMapping("/properties")
    ResponseEntity<List<Property>> getProperties() {
        return ResponseEntity.ok(this.propertyService.getProperties());
    }

    @PostMapping("/verifyproperty")
    ResponseEntity<Property> verifyProperty(@Valid @RequestBody VerifyPropertyDto verifyPropertyDto) {
        Property property = this.propertyService.verifyProperty(verifyPropertyDto.getPropertyId());
        return ResponseEntity.ok(property);
    }

    @PostMapping("/propertyfolder/{fileName:.+}")
    public void downloadPropertyFolder(HttpServletRequest request, HttpServletResponse response,
                                       @PathVariable("fileName") String fileName, @Valid @RequestBody DownloadPropertyFolderDto downloadPropertyFolderDto) throws Exception {

        String code = fileName.substring(0,fileName.lastIndexOf("."));
        this.storageService.zipPropertyFolder(downloadPropertyFolderDto.getUserId(),code);

        Path file = Paths.get("./static/properties/"+ downloadPropertyFolderDto.getUserId()+"/", fileName);
        sendFile(response, fileName, file);
    }


    //Contracts
    @GetMapping("/contracts")
    ResponseEntity<List<Contract>> getContracts() {
        List<Contract> contracts = this.contractService.getContracts();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/contracts/{id}")
    ResponseEntity<Contract> getContract(@PathVariable String id) {
        Contract contract = contractService.getContract(id);
        return ResponseEntity.ok(contract);
    }


    @PostMapping("/verifycontract")
    ResponseEntity<Contract> verifyContract(@Valid @RequestBody VerifyContractDto verifyContractDto) {
        Contract contract = contractService.verifyContract(verifyContractDto.getContractId());
        return ResponseEntity.ok(contract);
    }



    //Utility function to send file back to client
    private void sendFile(HttpServletResponse response, @PathVariable("fileName") String fileName, Path file) {
        if (Files.exists(file)) {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                System.out.println("Error :- " + e.getMessage());
            }
        } else {
            System.out.println("Sorry File not found!!!!");
        }
    }
}
