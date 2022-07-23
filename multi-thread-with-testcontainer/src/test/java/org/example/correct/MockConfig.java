package org.example.correct;

import org.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //Или можно TestConfiguration при настройке профилей
public class MockConfig {

    @Bean
    AccountGenerator accountGenerator(@Autowired AccountRepository repository) {
        return new AccountGenerator(repository);
    }

}
