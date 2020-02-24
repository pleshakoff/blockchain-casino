package com.casino.contract;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static com.google.common.io.ByteStreams.toByteArray;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractService {


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
        log.info("contract.give. Transaction {}",contract.give("0x1ad4f70b6a49d0448c1f1392db93793bff540e2b",BigInteger.valueOf(7)).send());
        log.info("contract.take. Transaction {}",contract.take("0x1ad4f70b6a49d0448c1f1392db93793bff540e2b",BigInteger.valueOf(2)));

        log.info("Balance {}",contract.balanceOf("0x1ad4f70b6a49d0448c1f1392db93793bff540e2b").send());

    }

    public byte[] makeAccount() {


        try {
            String keystore_path = WalletUtils.generateFullNewWalletFile("0", new File(""));
            InputStream in = getClass()
                    .getResourceAsStream(keystore_path);
            return toByteArray(in);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | CipherException | IOException e) {
            log.error("Creating account error",e);
           return null;
        }

    }

    public BigInteger getBalanceOf(String address) {

        try {
            return contract.balanceOf(address).send();
        } catch (Exception e) {
            log.error("Get balance of",e);
        }
        return null;
    }
}



