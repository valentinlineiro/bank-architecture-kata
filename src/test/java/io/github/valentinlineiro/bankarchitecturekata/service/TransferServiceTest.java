package io.github.valentinlineiro.bankarchitecturekata.service;

import io.github.valentinlineiro.bankarchitecturekata.model.TransferRequestDTOMother;
import io.github.valentinlineiro.bankarchitecturekata.repository.AccountRepositoryMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(TransferServiceTestConfiguration.class)
class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountRepositoryMock accountRepositoryMock;

    @Test
    void shouldTransferBetweenAccounts() {
        accountRepositoryMock.givenSource(100);
        accountRepositoryMock.givenDestination(0);
        transferService.transfer(TransferRequestDTOMother.example());
        accountRepositoryMock.thenAccountsBalanceAre(0, 100);
    }

    @Test
    void shouldNotTransferBetweenAccountsWhenSourceDoesNotExist() {
        final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer(TransferRequestDTOMother.example()));
        assertEquals("Account %s does not exist".formatted(AccountRepositoryMock.SOURCE), result.getMessage());
    }

    @Test
    void shouldNotTransferBetweenAccountsWhenDestinationDoesNotExist() {
        accountRepositoryMock.givenSource(0.00);
        final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer(TransferRequestDTOMother.example()));
        assertEquals("Account %s does not exist".formatted(AccountRepositoryMock.DESTINATION), result.getMessage());
    }

    @Test
    void shouldNotTransferBetweenAccountsWhenSourceDoesNotHaveEnoughBalanceToWithdraw() {
        accountRepositoryMock.givenSource(0);
        accountRepositoryMock.givenDestination(0);
        final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> transferService.transfer(TransferRequestDTOMother.example()));
        assertEquals("Account %s has not enough balance (%s) to withdraw %s".formatted(AccountRepositoryMock.SOURCE,
                "0", "100"), result.getMessage());
    }

}