package com.example.libraryapi;

import com.example.libraryapi.model.Author;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.repository.AuthorRepository;
import com.example.libraryapi.repository.BookRepository;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

@Component
public class DataLoader implements CommandLineRunner {

        private final AuthorRepository authorRepository;
        private final BookRepository bookRepository;

        public DataLoader(
                        AuthorRepository authorRepository,
                        BookRepository bookRepository) {
                this.authorRepository = authorRepository;
                this.bookRepository = bookRepository;
        }

        @Autowired
        private VaultTemplate vaultTemplate;

        @Override
        public void run(String... args) throws Exception {
                // seedUserToVault();
                seedDatabase();
        }

        private void seedUserToVault() {
                VaultKeyValueOperations keyValueOperations = vaultTemplate.opsForKeyValue("secret",
                                VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);

                System.out.println();
                System.out.println("Post secret" + Collections.singletonMap("user",
                                "pastaword").toString() + " to vault");
                System.out.println();

                keyValueOperations.put("secret", Collections.singletonMap("user",
                                "pastaword"));

                VaultResponse read = keyValueOperations.get("secret");
                System.out.println("Value of user password from vault [" +
                                read.getRequiredData().get("user") + "]");
        }

        private void seedDatabase() {
                if (authorRepository.count() > 0) {
                        return;
                }

                // Authors
                Author austen = authorRepository.save(createAuthor("Jane Austen"));
                Author bronte = authorRepository.save(createAuthor("Charlotte Brontë"));
                Author shelley = authorRepository.save(createAuthor("Mary Shelley"));
                Author woolf = authorRepository.save(createAuthor("Virginia Woolf"));
                Author alcott = authorRepository.save(createAuthor("Louisa May Alcott"));
                Author wharton = authorRepository.save(createAuthor("Edith Wharton"));
                Author gilman = authorRepository.save(createAuthor("Charlotte Perkins Gilman"));
                Author chopin = authorRepository.save(createAuthor("Kate Chopin"));
                Author gaskell = authorRepository.save(createAuthor("Elizabeth Gaskell"));
                Author burney = authorRepository.save(createAuthor("Frances Burney"));
                Author cather = authorRepository.save(createAuthor("Willa Cather"));
                Author jackson = authorRepository.save(createAuthor("Shirley Jackson"));

                // Books
                saveBook("Pride and Prejudice", austen, 1813, "9780141439518");
                saveBook("Sense and Sensibility", austen, 1811, "9780141439662");
                saveBook("Emma", austen, 1815, "9780141439587");

                saveBook("Jane Eyre", bronte, 1847, "9780141441146");

                saveBook("Frankenstein", shelley, 1818, "9780143131847");

                saveBook("A Room of One's Own", woolf, 1929, "9780156787338");
                saveBook("Mrs Dalloway", woolf, 1925, "9780156628709");
                saveBook("To the Lighthouse", woolf, 1927, "9780156907392");

                saveBook("Little Women", alcott, 1868, "9780147514011");

                saveBook("The Age of Innocence", wharton, 1920, "9780140189704");
                saveBook("The House of Mirth", wharton, 1905, "9780140187298");

                saveBook("The Yellow Wallpaper", gilman, 1892, "9781558611580");

                saveBook("The Awakening", chopin, 1899, "9780142437322");

                saveBook("North and South", gaskell, 1854, "9780141439815");

                saveBook("Evelina", burney, 1778, "9780199536931");

                saveBook("My Ántonia", cather, 1918, "9780140187649");

                saveBook("We Have Always Lived in the Castle", jackson, 1962, "9780141191454");

                System.out.println("Database seeded! :)");
        }

        private Author createAuthor(String name) {
                Author author = new Author();
                author.setName(name);
                return author;
        }

        private void saveBook(
                        String title,
                        Author author,
                        int year,
                        String isbn) {

                Book book = new Book();
                book.setTitle(title);
                book.setAuthor(author);
                book.setPublishedYear(year);
                book.setIsbn(isbn);

                bookRepository.save(book);
        }
}
