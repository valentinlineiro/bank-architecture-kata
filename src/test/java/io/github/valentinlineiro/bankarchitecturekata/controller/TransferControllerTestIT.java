package io.github.valentinlineiro.bankarchitecturekata.controller;

import io.github.valentinlineiro.bankarchitecturekata.TestApplication;
import io.github.valentinlineiro.bankarchitecturekata.model.AccountEntity;
import io.github.valentinlineiro.bankarchitecturekata.model.TransferRequestDTOMother;
import io.github.valentinlineiro.bankarchitecturekata.repository.AccountEntityRepository;
import io.github.valentinlineiro.bankarchitecturekata.repository.AccountRepositoryMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TransferControllerTestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    @Test
    void shouldTransferBetweenAccounts() {
        executeWithinDatabaseContext(() -> {
            TestRestTemplate restTemplate = new TestRestTemplate();
            ResponseEntity<Void> result =
                    restTemplate.postForEntity("http://localhost:%d/v1/api/transference".formatted(port),
                            new HttpEntity<>(TransferRequestDTOMother.example(), new HttpHeaders()), Void.class);
            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            assertEquals(new BigDecimal(100),
                    accountEntityRepository.findById(UUID.fromString(AccountRepositoryMock.SOURCE)).get().getBalance());
            assertEquals(new BigDecimal(150),
                    accountEntityRepository.findById(UUID.fromString(AccountRepositoryMock.DESTINATION)).get().getBalance());
        });
    }

    private void executeWithinDatabaseContext(Runnable method) {
        try {
            prepareDatabase();
            method.run();
        } finally {
            cleanDatabase();
        }
    }

    private void prepareDatabase() {
        accountEntityRepository.save(new AccountEntity(UUID.fromString(AccountRepositoryMock.SOURCE),
                BigDecimal.valueOf(200)));
        accountEntityRepository.save(new AccountEntity(UUID.fromString(AccountRepositoryMock.DESTINATION),
                BigDecimal.valueOf(50)));
    }

    private void cleanDatabase() {
        accountEntityRepository.deleteAllById(List.of(UUID.fromString(AccountRepositoryMock.SOURCE),
                UUID.fromString(AccountRepositoryMock.DESTINATION)));
    }

}