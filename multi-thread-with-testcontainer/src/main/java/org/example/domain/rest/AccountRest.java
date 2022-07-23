package org.example.domain.rest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountRest {

    private Long id;
    private int accountNumber;
    private Long sumMoneyRub;
    private Long userId;
    private LocalDateTime createdAt;

}
