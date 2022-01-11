package com.users.service.controller;

import com.users.service.auth.models.AuthUser;
import com.users.service.dto.createcontract.CreateBasicContractDto;
import com.users.service.dto.createcontract.CreateBlackListedContractDto;
import com.users.service.dto.createcontract.CreateWhiteListedContractDto;
import com.users.service.entity.Contract;
import com.users.service.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequestMapping("/users/contracts")
public class ContractController {

    @Autowired
    ContractService contractService;


    @PostMapping("/createbasic")
    ResponseEntity<Contract> createBasic(@Valid @RequestBody CreateBasicContractDto createBasicContractDto, @AuthenticationPrincipal AuthUser authUser) {
        Contract contract = this.contractService.submitContract(createBasicContractDto,authUser.getUid());
        return ResponseEntity.ok(contract);
    }

    @PostMapping("/createwhitelisted")
    ResponseEntity<Contract> createWhiteListed(@Valid @RequestBody CreateWhiteListedContractDto createWhiteListedContractDto, @AuthenticationPrincipal AuthUser authUser) {
        Contract contract = this.contractService.submitContract(createWhiteListedContractDto,authUser.getUid());
        return ResponseEntity.ok(contract);
    }

    @PostMapping("/createblacklisted")
    ResponseEntity<Contract> createBlackListed(@Valid @RequestBody CreateBlackListedContractDto createBlackListedContractDto, @AuthenticationPrincipal AuthUser authUser) {
        Contract contract = this.contractService.submitContract(createBlackListedContractDto,authUser.getUid());
        return ResponseEntity.ok(contract);
    }
}
