package com.casino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@SpringBootApplication
public class BlockchainCasinoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainCasinoApplication.class, args);
	}


	@Bean
	Web3j web3(){
		return Web3j.build(new HttpService("http://127.0.0.1:8545"));
	}


}
