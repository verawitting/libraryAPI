package com.example.libraryapi.controller;

import com.example.libraryapi.dto.LoanRequest;
import com.example.libraryapi.dto.LoanResponse;
import com.example.libraryapi.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new loan")
    @ApiResponse(responseCode = "201", description = "Loan created successfully")
    @ApiResponse(responseCode = "400", description = "Book is already on loan")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody LoanRequest request) {
        return ResponseEntity.status(201).body(service.createLoan(request));
    }

    @Operation(summary = "Get loan by id")
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLoanById(id));
    }

    @Operation(summary = "Get all loans")
    @ApiResponse(responseCode = "200", description = "List of loans")
    @GetMapping
    public ResponseEntity<Page<LoanResponse>> getAllLoans(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.getAllLoans(pageable));
    }

    @Operation(summary = "Update a loan (like a return)")
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponse> updateLoan(@PathVariable Long id) {
        return ResponseEntity.ok(service.updateLoan(id));
    }

    @Operation(summary = "Delete loan by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        service.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }
}
