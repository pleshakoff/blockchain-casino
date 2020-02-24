package com.casino.game;

import javax.validation.Valid;

public interface GameService {

    String getBalanceOf(String address);

    void withdraw(String address);

    String play(String address, @Valid BetDto betDto);
}
