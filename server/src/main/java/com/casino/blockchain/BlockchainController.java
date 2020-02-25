package com.casino.blockchain;

import com.casino.game.BetDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/blockchain", produces = {MediaType.TEXT_PLAIN_VALUE})
@Api(tags = "Blockchain")
@RequiredArgsConstructor
class BlockchainController {

    private final BlockchainService blockchainService;

    @GetMapping(value = "/balance/{address}")
    @ApiOperation(value = "Get balance by address")
    public String getBalanceOf(@PathVariable String address) {
        return blockchainService.getBalance(address).toString();
    }




}
