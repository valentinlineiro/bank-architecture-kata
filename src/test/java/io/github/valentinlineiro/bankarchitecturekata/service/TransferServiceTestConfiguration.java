package io.github.valentinlineiro.bankarchitecturekata.service;

import io.github.valentinlineiro.bankarchitecturekata.repository.AccountEntityRepository;
import io.github.valentinlineiro.bankarchitecturekata.repository.AccountRepositoryMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class TransferServiceTestConfiguration {

    @MockBean
    private AccountEntityRepository accountEntityRepository;

    @Bean
    public TransferService transferService() {
        return new TransferService(accountEntityRepository);
    }

    @Bean
    public AccountRepositoryMock accountRepositoryMock() {
        return new AccountRepositoryMock(accountEntityRepository);
    }

}
