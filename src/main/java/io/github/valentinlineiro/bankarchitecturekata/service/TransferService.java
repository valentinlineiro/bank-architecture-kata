package io.github.valentinlineiro.bankarchitecturekata.service;

import io.github.valentinlineiro.bankarchitecturekata.model.AccountEntity;
import io.github.valentinlineiro.bankarchitecturekata.model.TransferRequestDTO;
import io.github.valentinlineiro.bankarchitecturekata.repository.AccountEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;

@Service
public class TransferService {

    private final AccountEntityRepository accountEntityRepository;

    public TransferService(final AccountEntityRepository accountEntityRepository) {
        this.accountEntityRepository = accountEntityRepository;
    }

    @Transactional
    public void transfer(final TransferRequestDTO request) {
        final AccountEntity source = accountEntityRepository.findById(request.source())
                .orElseThrow(() -> new IllegalArgumentException("Account %s does not exist".formatted(request.source())));
        final AccountEntity destination = accountEntityRepository.findById(request.destination())
                .orElseThrow(() -> new IllegalArgumentException("Account %s does not exist".formatted(request.destination())));
        if (!canWithdraw(source, request.amount())) {
            throw new IllegalArgumentException("Account %s has not enough balance (%s) to withdraw %s".formatted(
                    source.getId(), NumberFormat.getInstance().format(source.getBalance()),
                    NumberFormat.getInstance().format(request.amount())));
        }
        source.setBalance(source.getBalance().subtract(request.amount()));
        destination.setBalance(destination.getBalance().add(request.amount()));
        accountEntityRepository.save(source);
        accountEntityRepository.save(destination);
    }

    private boolean canWithdraw(final AccountEntity account, final BigDecimal amount) {
        return account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

}
