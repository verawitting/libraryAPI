package com.example.libraryapi.controller;

import com.example.libraryapi.dto.LoanRequest;
import com.example.libraryapi.dto.LoanResponse;
import com.example.libraryapi.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get all loans")
    @ApiResponse(responseCode = "200", description = "List of loans")
    @GetMapping
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        return ResponseEntity.ok(service.getAllLoans());
    }
}
