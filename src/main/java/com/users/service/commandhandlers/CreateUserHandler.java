package com.users.service.commandhandlers;


import com.users.service.auth.models.AuthUser;
import com.users.service.commands.CreateUserCommand;
import com.users.service.dto.CreateUserDto;
import com.users.service.entity.User;
import com.users.service.repository.UserRepository;
import com.users.service.services.StorageService;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;



@Component
public class CreateUserHandler {
    @Autowired
    UserRepository userRepo;
    @Autowired
    StorageService storageService;
    @CommandHandler()
    public void handle(CreateUserCommand command) throws IOException {
        boolean userExists = this.userRepo.existsById(command.getUid());
        this.storageService.downloadFile("uploads", "test.htm");

        if(!userExists){
            AuthUser authUser = command.getAuthUser();
            CreateUserDto createUserDto = command.getCreateUserDto();





            User user;

            user =  new User(
                    authUser.getUid(),
                    authUser.getEmail(),
                    createUserDto.getFirstName(),
                    createUserDto.getLastName(),
                    createUserDto.getPhone(),
                    createUserDto.getAddress(),
                    authUser.isEmailVerified(),
                    false,
                    authUser.getIssuer(),
                    authUser.getPicture()
            );

            this.userRepo.save(user);

        }


    }
}
