package com.users.service.commands;


import com.users.service.auth.models.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@AllArgsConstructor
public class CreateUserCommand {
    @TargetAggregateIdentifier
    public String uid;
    public AuthUser authUser;
}
