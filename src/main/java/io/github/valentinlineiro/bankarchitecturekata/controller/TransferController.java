package io.github.valentinlineiro.bankarchitecturekata.controller;

import io.github.valentinlineiro.bankarchitecturekata.model.TransferRequestDTO;
import io.github.valentinlineiro.bankarchitecturekata.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class TransferController {

    private final TransferService transferService;

    public TransferController(final TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("transference")
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequestDTO request) {
        transferService.transfer(request);
        return ResponseEntity.noContent().build();
    }

}
