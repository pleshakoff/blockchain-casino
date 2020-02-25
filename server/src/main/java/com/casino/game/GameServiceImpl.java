package com.casino.game;

import com.casino.contract.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Service
@Slf4j
@RequiredArgsConstructor
class GameServiceImpl implements GameService {

    private final ContractService contractService;


    @Override
    public String getBalanceOf(String address) {
            return contractService.getBalanceOf(address).toString();
    }



    @Override
    public void withdraw(String address) {
        contractService.withdraw(address);
    }


    private Short getRandom() {
       return 42; // :)
    }

    @Override
    public String play(String address, @Valid BetDto betDto) {

        @NotNull BigInteger bet = betDto.getBet();
        if (contractService.getBalanceOf(address).compareTo(bet)<0) {
            throw new NotEnoughPlayerMoneyException();
        }
        if (betDto.getGuess().equals(getRandom())) {
            if (contractService.getBalanceOf(contractService.getContractAddress()).compareTo(bet)<0) {
                throw new NotEnoughCasinoMoneyException();
            }
            contractService.give(address, bet);
            return "You are WINNER!!!! Check your balance, lucky!!!!";
        } else {
            contractService.take(address,bet);
            return "You are LOOSER :((( Next time will be better. See you";
        }
    }
}
