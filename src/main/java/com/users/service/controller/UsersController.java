package com.users.service.controller;


import com.users.service.auth.models.AuthUser;
import com.users.service.commands.CreateUserCommand;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UsersController {
    private CommandGateway commandGateway;

    public UsersController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }


    @GetMapping()
    public String register(@AuthenticationPrincipal AuthUser athUser){
        CreateUserCommand createUserCommand = new CreateUserCommand(athUser.getUid(),athUser);
        commandGateway.sendAndWait(createUserCommand);
        return "User Created";
    }

}
