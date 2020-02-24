package com.casino.contract;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;


@RestController
@RequestMapping(value = "/address", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags = "Contract")
@RequiredArgsConstructor
class ContractController {

    private final ContractService contractService;

    @GetMapping(produces = {MediaType.TEXT_PLAIN_VALUE})
    @ApiOperation(value = "Get contract address")
    public String getCurrentPeerState() {
        return contractService.getContractAddress();
    }


    @GetMapping(produces = {MediaType.TEXT_PLAIN_VALUE})
    @ApiOperation(value = "Get contract address")
    public BigInteger getBalanceOf(@PathVariable String address) {

        return contractService.getBalanceOf(address);
    }

}
