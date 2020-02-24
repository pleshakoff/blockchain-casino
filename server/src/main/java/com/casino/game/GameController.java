package com.casino.game;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "", produces = {MediaType.TEXT_PLAIN_VALUE})
@Api(tags = "Game")
@RequiredArgsConstructor
class GameController {

    private final GameServiceImpl gameService;



    @GetMapping(value = "/balance/{address}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ApiOperation(value = "Get balance by address")
    public String getBalanceOf(@PathVariable String address) {
        return gameService.getBalanceOf(address);
    }

    @PostMapping(value = "/withdraw/{address}")
    @ApiOperation(value = "Withdraw money from casino")
    public void withdraw(@PathVariable String address) {
        gameService.withdraw(address);
    }

    @PostMapping(value = "/play/{address}")
    @ApiOperation(value = "Play game")
    public String play(@PathVariable String address,@Valid @RequestBody BetDto betDto,
                       BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return gameService.play(address,betDto);
    }



}
