package com.users.service;

import com.users.service.entity.DeployedContract;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, DeployedContract> kafkaTemplate) {
		DeployedContract contract = new DeployedContract();
		contract.setContractAddress("asdasd");
		contract.setDescription("asdasd");
		contract.setId("asdasd");
		contract.setPrice(12.2f);
		contract.setPropertyAddress("asdasd");
		contract.setTitle("asdasd");
		contract.setSellerWallet("asdasd");
		contract.setType("asdasd");
		contract.setPropertyCode("asdasd");


		return args -> {
			kafkaTemplate.send("contract" , contract);
		};
	}


}
