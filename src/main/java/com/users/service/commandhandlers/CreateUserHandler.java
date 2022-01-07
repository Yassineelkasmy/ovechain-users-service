package com.users.service.commandhandlers;


import com.users.service.auth.models.AuthUser;
import com.users.service.commands.CreateUserCommand;
import com.users.service.entity.User;
import com.users.service.repository.UserRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CreateUserHandler {
    @Autowired UserRepository userRepo;
    @CommandHandler()
    public Boolean handle(CreateUserCommand command) {
        Boolean userExists = this.userRepo.existsById(command.getUid());
        if(!userExists){
            AuthUser authUser = command.getAuthUser();
            User user;
           
        }

        return userExists;

    }
}
