package com.casino.blockchain;

import java.math.BigDecimal;

public interface BlockchainService {
    String send(SendDto dto);

    BigDecimal getBalance(String address);
}
