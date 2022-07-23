package org.example.correct;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.AccountEntity;
import org.example.repository.AccountRepository;

import java.security.SecureRandom;

@RequiredArgsConstructor
public class AccountGenerator {

    private final AccountRepository accountRepository;

    private final static SecureRandom SECURE_RANDOM = new SecureRandom();

    public AccountEntity generateRandom() {
        return saveToAccountRepository(
                SECURE_RANDOM.nextInt(),
                SECURE_RANDOM.nextLong(),
                SECURE_RANDOM.nextLong()
        );
    }

    public AccountEntity generateWithSelectSum(Long sum) {
        return saveToAccountRepository(
                SECURE_RANDOM.nextInt(),
                sum,
                SECURE_RANDOM.nextLong()
        );
    }

    private AccountEntity saveToAccountRepository(int accountNumber, Long moneyRub, Long userId) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountNumber(accountNumber);
        accountEntity.setSumMoneyRub(moneyRub);
        accountEntity.setUserId(userId);
        return accountRepository.save(accountEntity);
    }
}
