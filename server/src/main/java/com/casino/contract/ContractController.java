package com.casino.contract;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "",produces = {MediaType.TEXT_PLAIN_VALUE})
@Api(tags = "Contract")
@RequiredArgsConstructor
class ContractController {

    private final ContractService contractService;

    @GetMapping(value = "/address")
    @ApiOperation(value = "Get contract address")
    public String getCurrentPeerState() {
        return contractService.getContractAddress();
    }


}
