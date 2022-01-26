package com.users.service.services;

import com.users.service.dto.createcontract.CreateContractDto;
import com.users.service.entity.Contract;
import com.users.service.entity.Property;
import com.users.service.enums.SmartContractType;
import com.users.service.repository.ContractRepository;
import com.users.service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContractService {
    @Autowired
    StorageService storageService;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    ContractRepository contractRepository;

    public List<Contract> getContracts() {
        return contractRepository.findAll();
    }

    public Contract getContract(String contractID) {
        return contractRepository.findById(contractID).get();
    }

    public Contract submitContract(CreateContractDto createContractDto, String uid) {
        Property property = new Property();
        property.setUserId(uid);
        property.setCode(createContractDto.getPropertyCode());

        Property candidate  = propertyRepository.findOne(Example.of(property)).get();

        if(candidate.getContractId() == null){
            Contract contract = new Contract(
                    UUID.randomUUID().toString(),
                    uid,
                    candidate.getId(),
                    null,
                    createContractDto.getWallet(),
                    createContractDto.getPrice(),
                    false,
                    false,
                    SmartContractType.BASIC,
                    createContractDto.getWhiteListWallets(),
                    createContractDto.getBlackListWallets()
            );
            Contract newContract =  contractRepository.save(contract);
            candidate.setContractId(newContract.getId());
            this.propertyRepository.save(candidate);
            return newContract;
        }
        else {
            Contract contract = new Contract();
            contract.setPropertyId(property.getId());
            return contractRepository.findOne(Example.of(contract)).get();
        }

    }

    public Contract verifyContract(String contractId) {
        Contract contract = contractRepository.findById(contractId).get();
        contract.setIsVerified(true);
        return contractRepository.save(contract);
    }

    public Contract deployContract(String contractId,String contractAddress) {
        System.out.println("inside deploy contract service");
        Contract contract = contractRepository.findById(contractId).get();
        contract.setContractAddress(contractAddress);
        contract.setIsDeployed(true);
        System.out.println("contract deployed : "+contract.getIsDeployed());
        return contractRepository.save(contract);
    }
}
