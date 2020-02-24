package com.casino.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "You don't have enough money for this bet")
public class NotEnoughPlayerMoneyException extends RuntimeException {

    public NotEnoughPlayerMoneyException() {
        super("You don't have enough money for bet");
    }
}
