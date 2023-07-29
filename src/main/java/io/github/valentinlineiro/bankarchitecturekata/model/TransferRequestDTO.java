package io.github.valentinlineiro.bankarchitecturekata.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDTO(@NotNull(message = "Source account must be provided") UUID source,
                                 @NotNull(message = "Destination account must be provided") UUID destination,
                                 @DecimalMin(value = "0", inclusive = false, message = "Amount to transfer must be " +
                                         "greater than zero") BigDecimal amount) {
}
