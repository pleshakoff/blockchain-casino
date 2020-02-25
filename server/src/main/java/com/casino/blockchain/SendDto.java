package com.casino.blockchain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class SendDto {

     @NotNull
     private final String from;

     @NotNull
     private final String to;

     @NotNull
     private final BigDecimal value;

     @NotNull
     private final String pass;

}
