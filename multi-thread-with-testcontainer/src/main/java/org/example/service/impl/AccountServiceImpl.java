package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.example.domain.entity.AccountEntity;
import org.example.domain.rest.AccountRest;
import org.example.mapper.AccountMapper;
import org.example.repository.AccountRepository;
import org.example.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<Long> getAllIds() {
        return accountRepository.findAll().stream().map(AccountEntity::getId).collect(Collectors.toList());
    }

    @Override
    public AccountRest get(Long accountId) {
        var entity = accountRepository.getById(accountId);
        return AccountMapper.INSTANCE.map(entity);
    }

    @Override
    public AccountRest post(AccountRest account) {
        var entity = AccountMapper.INSTANCE.map(account);
        var savedEntity = accountRepository.save(entity);
        return AccountMapper.INSTANCE.map(savedEntity);
    }
}
