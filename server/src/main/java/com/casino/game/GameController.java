package com.casino.game;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags = "Game")
@RequiredArgsConstructor
class GameController {

    private final GameService gameService;


    @GetMapping(value = "/balance/{address}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ApiOperation(value = "Get balance by address")
    public String getBalanceOf(@PathVariable String address) {
        return gameService.getBalanceOf(address);
    }

    @GetMapping(value = "/withdraw/{address}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ApiOperation(value = "Withdraw money from casino")
    public void withdraw(@PathVariable String address) {
        gameService.withdraw(address);
    }


}
