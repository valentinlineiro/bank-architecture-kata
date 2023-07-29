package io.github.valentinlineiro.bankarchitecturekata.repository;

import io.github.valentinlineiro.bankarchitecturekata.model.AccountEntity;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public record AccountRepositoryMock(AccountEntityRepository accountEntityRepository) {

    public static final String SOURCE = "5c82ca20-9aa2-49f0-9ca4-fcc5f42f766c";
    public static final String DESTINATION = "d9b3fbf8-b495-448f-bc33-2e1889e2105b";

    public void givenSource(double initialAmount) {
        givenAccount(UUID.fromString(SOURCE), initialAmount);
    }

    public void givenDestination(double initialAmount) {
        givenAccount(UUID.fromString(DESTINATION), initialAmount);
    }

    public void thenAccountsBalanceAre(double source, double destination) {
        ArgumentCaptor<AccountEntity> account = ArgumentCaptor.forClass(AccountEntity.class);
        verify(accountEntityRepository, times(2)).save(account.capture());
        assertEquals(new BigDecimal(source), account.getAllValues().get(0).getBalance());
        assertEquals(new BigDecimal(destination), account.getAllValues().get(1).getBalance());
    }

    private void givenAccount(UUID id, double initialAmount) {
        when(accountEntityRepository.findById(id)).thenReturn(Optional.of(new AccountEntity(id,
                new BigDecimal(initialAmount))));
    }

}
