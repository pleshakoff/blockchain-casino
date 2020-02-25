package com.casino.blockchain;


import com.casino.credentials.AddressesProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlockchainServiceImpl implements BlockchainService {

    private final Web3j web3;
    private final AddressesProps addressesProps;


    @Override
    public String send(SendDto dto) {

        try {
            log.info("Send {} Ether from {} to {}",dto.getValue(),dto.getFrom(),dto.getTo());
            Credentials credentials = addressesProps.getCredentialsByName(dto.getFrom(),dto.getPass());
            TransactionReceipt transferReceipt = Transfer.sendFunds(
                    web3, credentials,
                    dto.getTo(),
                    dto.getValue(), Convert.Unit.ETHER)  // 1 wei = 10^-18 Ether
                    .sendAsync().get();

            String hash = String.format("Transaction complete : %s"
                    , transferReceipt.getTransactionHash());
            log.info(hash);
            return hash;
        } catch (Exception e) {
            log.error("Get balance of",e);
            throw  new BlockchainException();
        }
    }

    @Override
    public BigDecimal getBalance(String address) {

        try {
            log.info("Get balance  for {}",address);
            EthGetBalance ethGetBalance = web3
                    .ethGetBalance(address, DefaultBlockParameterName.LATEST)
                    .send();
             return Convert.fromWei(ethGetBalance.getBalance().toString(), Unit.ETHER);
        } catch (Exception e) {
            log.error("Get balance of",e);
            throw  new BlockchainException();
        }
    }




}
