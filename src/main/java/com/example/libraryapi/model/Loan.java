package com.example.libraryapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Den här koden används för att testa concurrency.
    // När shouldFailWithConcurrentLoans() körs i LoanIntegrationTests
    // skapades 3 utlån av samma bok. Med den version av fältet som är nedan denna
    // skapades enbart ett lån när jag använde flera trådar samtidigt på samma creatLoan metod.
//    @ManyToOne
//    @JoinColumn(name = "book_id")
//    private Book book;

    // Detta är det fält som jag faktiskt vill använda, på grund av extra säkerhet.
    // Jag använder oneToOne relation och unique för att försäkra mig om att bara ett lån
    // kan skapas för en bok.
    @OneToOne
    @JoinColumn(name = "book_id", unique = true)
    private Book book;


    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan() {
    }

    public Loan(Book book) {
        this.book = book;
        this.loanDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;

    }
}