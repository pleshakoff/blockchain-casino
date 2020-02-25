package com.casino.blockchain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Blockchain interaction error")
public class BlockchainException extends RuntimeException {

    public BlockchainException() {
        super("Blockchain interaction error");
    }
}
