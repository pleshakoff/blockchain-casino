package com.casino.game;

import com.casino.contract.ContractService;
import com.casino.exceptions.SmartContractException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

    private final ContractService contractService;


    public String getBalanceOf(String address) {

        try {
            log.info("Get balance  for {}",address);
            return contractService.getContract().balanceOf(address).send().toString();
        } catch (Exception e) {
            log.error("Get balance of",e);
            throw  new SmartContractException();
        }
    }


    public void withdraw(String address) {

        try {
            log.info("Withdraw  for {}",address);
            contractService.getContract().withdraw(address, BigInteger.valueOf(1000000)).send();
        } catch (Exception e) {
            log.error("Withdraw",e);
            throw  new SmartContractException();
        }
    }

}
