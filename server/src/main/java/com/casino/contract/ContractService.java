package com.casino.contract;

import java.math.BigInteger;

public interface ContractService {

    String getContractAddress();

    BigInteger getBalanceOf(String address);

    void withdraw(String address);

    void give(String address, BigInteger value);

    void take(String address, BigInteger value);
}
