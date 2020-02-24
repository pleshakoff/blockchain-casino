package com.casino.contract;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Smart contract operation error")
public class SmartContractException extends RuntimeException {

    public SmartContractException() {
        super("Sorry. I am not on duty today :(");
    }
}
