package com.casino.credentials;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
@ConfigurationProperties(prefix = "blockchain")
public class AddressesProps {

    private List<Address> addresses =  new ArrayList<>();

    public Credentials getCredentialsByName(String name,String pass){
        String file = addresses.stream().
                filter(addressItem -> addressItem.getName().equals(name)).
                findFirst().
                map(Address::getFile).
                orElseThrow(() -> new RuntimeException("Unsupported address "+name));

        Credentials credentials;
        try {
            credentials = WalletUtils.loadCredentials(
                    pass,
                    file);

        } catch (IOException | CipherException e) {
            log.error("Credentials loading error",e);
            throw new  RuntimeException(e);
        }
        log.info("Credentials loaded");
        return credentials;
    }
}
