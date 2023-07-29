package io.github.valentinlineiro.bankarchitecturekata.model;

import io.github.valentinlineiro.bankarchitecturekata.repository.AccountRepositoryMock;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRequestDTOMother {

    public static TransferRequestDTO example() {
        return new TransferRequestDTO(UUID.fromString(AccountRepositoryMock.SOURCE),
                UUID.fromString(AccountRepositoryMock.DESTINATION), BigDecimal.valueOf(100));
    }

}
