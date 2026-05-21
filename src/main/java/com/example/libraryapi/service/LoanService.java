package com.example.libraryapi.service;

import com.example.libraryapi.dto.LoanRequest;
import com.example.libraryapi.dto.LoanResponse;
import com.example.libraryapi.exception.BookAlreadyOnLoanException;
import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.model.Loan;
import com.example.libraryapi.repository.BookRepository;
import com.example.libraryapi.repository.LoanRepository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public LoanResponse createLoan(LoanRequest request) {

        Book book = bookRepository.findByIdForUpdate(request.bookId())
                .orElseThrow(() -> new BookNotFoundException(request.bookId()));

        loanRepository.findByBookId(book.getId()).ifPresent(l -> {
            throw new BookAlreadyOnLoanException(book.getId());
        });

        Loan loan = new Loan(book);
        Loan saved = loanRepository.save(loan);

        return mapToResponse(saved);
    }

    public LoanResponse getLoanById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return mapToResponse(loan);
    }

    public Page<LoanResponse> getAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    //return book!
    @Transactional
    public LoanResponse updateLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        
        loan.setReturnDate(LocalDate.now());

        return mapToResponse(loanRepository.save(loan));
    }

    public void deleteLoan(Long id) {
        
        if (!loanRepository.existsById(id)) {
            throw new RuntimeException("Loan not found");
        }

        loanRepository.deleteById(id);
    }

    private LoanResponse mapToResponse(Loan loan) {
        return new LoanResponse(
            loan.getId(),
            loan.getBook().getId(),
            loan.getBook().getTitle(),
            loan.getLoanDate(),
            loan.getReturnDate()
        );
    }
}