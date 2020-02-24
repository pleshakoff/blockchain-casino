package com.casino.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Casino don't have enough money for this bet")
public class NotEnoughCasinoMoneyException extends RuntimeException {

    public NotEnoughCasinoMoneyException() {
        super("Casino don't have enough money for your bet");
    }
}
