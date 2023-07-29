package io.github.valentinlineiro.bankarchitecturekata.repository;

import io.github.valentinlineiro.bankarchitecturekata.model.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AccountEntityRepository extends MongoRepository<AccountEntity, UUID> {
}
