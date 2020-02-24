package com.casino.contract;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

@Service
@Slf4j
@RequiredArgsConstructor
class ContractServiceImpl implements ContractService {


    private final Web3j web3;

    @Getter
    private String contractAddress;

    @Getter
    private Lucky contract;


    @PostConstruct
    void init() {
        try {
            log.info("Client version: {}", web3.web3ClientVersion().send().getWeb3ClientVersion());
            log.info("Block number: {}", web3.ethBlockNumber().send().getBlockNumber());
            log.info("Gas price: {}", web3.ethGasPrice().send().getGasPrice());
            deploy();
        } catch (Exception ex) {
            log.error("Blockchain exchange error", ex);
        }
    }


    private void deploy() throws Exception {
        Credentials credentials =
                WalletUtils.loadCredentials(
                        "0",
                        "UTC--2020-02-15T23-15-17.397221865Z--e7b0600cd184432527b3ea401eebb5dc5d05b855");
        log.info("Credentials loaded");
        log.info("Deploying smart contract");
        contract = Lucky.deploy(
                web3, credentials,
                new DefaultGasProvider(),BigInteger.valueOf(1000000)).send();

        contractAddress = contract.getContractAddress();
        log.info("Smart contract deployed to address " + contractAddress);
    }

    @Override
    public BigInteger getBalanceOf(String address) {

        try {
            log.info("Get balance  for {}",address);
            return contract.balanceOf(address).send();
        } catch (Exception e) {
            log.error("Get balance of",e);
            throw  new SmartContractException();
        }
    }

    @Override
    public void withdraw(String address) {

        try {
            log.info("Withdraw  for {}",address);
            contract.withdraw(address, BigInteger.ZERO).send();
        } catch (Exception e) {
            log.error("Withdraw",e);
            throw  new SmartContractException();
        }
    }

    @Override
    public void give(String address, BigInteger value) {

        try {
            log.info("Send money {} for {}",value,address);
            contract.give(address, value).send();
        } catch (Exception e) {
            log.error("Send money",e);
            throw  new SmartContractException();
        }
    }

    @Override
    public void take(String address, BigInteger value) {

        try {
            log.info("Take money {} from {}",value,address);
            contract.take(address, value).send();
        } catch (Exception e) {
            log.error("Send money",e);
            throw  new SmartContractException();
        }
    }




}



