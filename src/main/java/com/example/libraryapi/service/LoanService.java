package com.example.libraryapi.service;

import com.example.libraryapi.dto.LoanRequest;
import com.example.libraryapi.dto.LoanResponse;
import com.example.libraryapi.exception.BookAlreadyOnLoanException;
import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.model.Loan;
import com.example.libraryapi.repository.BookRepository;
import com.example.libraryapi.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        Book book = bookRepository.findByIdForUpdate(request.BookId())
                .orElseThrow(() -> new BookNotFoundException(request.BookId()));

        loanRepository.findByBookId(book.getId()).ifPresent(l -> {
            throw new BookAlreadyOnLoanException(book.getId());
        });

        Loan loan = new Loan(book);
        Loan saved = loanRepository.save(loan);

        return new LoanResponse(saved.getId(), book.getId(), book.getTitle(), saved.getLoanDate(), saved.getReturnDate()
        );
    }

    public List<LoanResponse> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(l -> new LoanResponse(
                        l.getId(),
                        l.getBook().getId(),
                        l.getBook().getTitle(),
                        l.getLoanDate(),
                        l.getReturnDate()
                ))
                .toList();
    }

    public void updateLoan() {
        //implement this!
    }

    public void deleteLoan() {
        //implement this!
    }
}
