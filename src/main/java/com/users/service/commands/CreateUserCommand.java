package com.users.service.commands;


import com.users.service.auth.models.AuthUser;
import com.users.service.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@AllArgsConstructor
public class CreateUserCommand {
    @TargetAggregateIdentifier
    public String uid;
    public AuthUser authUser;
    public CreateUserDto createUserDto;
}
