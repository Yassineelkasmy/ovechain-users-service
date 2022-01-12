package com.users.service.services;

import com.users.service.dto.CreateUserDto;
import com.users.service.entity.User;
import com.users.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    @Autowired
    StorageService storageService;

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers() {

        return this.userRepository.findAll();
    }

    public User getUser(String uid) {
        User user = this.userRepository.findById(uid).get();
        return user;
    }


    public User registerUser(CreateUserDto createUserDto, String uid, String email) throws IOException, ExecutionException, InterruptedException {
        boolean userExists = this.userRepository.existsById(uid);
        User user;
        if(userExists) {
            user = this.userRepository.findById(uid).get();

            return user;
        }
        else {
            this.storageService.donwloadUserFolder(uid);

            User newUser = new User(
                    uid,
                    email,
                    createUserDto.getFirstName(),
                    createUserDto.getLastName(),
                    createUserDto.getPhone(),
                    createUserDto.getAddress(),
                    false
            );

            user = this.userRepository.save(newUser);

        }

        return user;

    }
}
