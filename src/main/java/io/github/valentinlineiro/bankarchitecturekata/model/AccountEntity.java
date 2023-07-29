package io.github.valentinlineiro.bankarchitecturekata.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Document(collection = "documents")
@Data
public class AccountEntity {

    @Id
    @NonNull
    private UUID id;

    @NonNull
    private BigDecimal balance;

}
