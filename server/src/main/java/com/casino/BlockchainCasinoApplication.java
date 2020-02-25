package com.casino;

import com.casino.credentials.AddressesProps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@SpringBootApplication
@EnableConfigurationProperties(AddressesProps.class)
public class BlockchainCasinoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainCasinoApplication.class, args);
	}


	@Value("${blockchain.host}")
	String host;

	@Value("${blockchain.port}")
	String port;


	@Bean
	Web3j web3(){
		return Web3j.build(new HttpService(String.format("http://%s:%s",host,port)));
	}


}
