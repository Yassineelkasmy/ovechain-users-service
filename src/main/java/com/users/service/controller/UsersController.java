package com.users.service.controller;


import com.users.service.auth.models.AuthUser;
import com.users.service.commands.CreateUserCommand;

import com.users.service.dto.CreateUserDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class UsersController {
    private CommandGateway commandGateway;


    public UsersController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;

    }


    @PostMapping()
    public String register(@Valid @RequestBody CreateUserDto createUserDto, @AuthenticationPrincipal AuthUser athUser){
        CreateUserCommand createUserCommand = new CreateUserCommand(athUser.getUid(),athUser,createUserDto);
        commandGateway.sendAndWait(createUserCommand);
        return "User Created";
    }


}
