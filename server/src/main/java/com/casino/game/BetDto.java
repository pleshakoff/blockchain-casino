package com.casino.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.math.BigInteger;

@RequiredArgsConstructor
@Getter
public class BetDto {

    @NotNull
    private final BigInteger bet;

    @NotNull
    @PositiveOrZero(message = "Guess must be positive or zero")
    @Max(value = 50,message = "Guess can't be greater then 50")
    private final Short guess;

}
