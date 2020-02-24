package com.casino.contract;

import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class MyGasProvider extends StaticGasProvider {
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(600590);
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(1000000000);

    public MyGasProvider() {
        super(GAS_PRICE, GAS_LIMIT);
    }
}
