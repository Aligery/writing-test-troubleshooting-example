package org.example.service;

import org.example.domain.rest.AccountRest;

import java.util.List;

public interface AccountService {

    List<Long> getAllIds();

    AccountRest get(Long accountId);

    AccountRest post(AccountRest account);

}
