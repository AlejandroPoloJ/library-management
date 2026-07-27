package pe.com.apolo.domain.model.book;

import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;

import java.time.LocalDate;

public class Book {

    private final BookId id;
    private String title;
    private ISBN isbn;
    private String author;
    private int pageCount;
    private LocalDate publicationDate;

    public Book(BookId id, String title, ISBN isbn, String author,
                int pageCount, LocalDate publicationDate) {
        validateTitle(title);
        validateISBN(isbn);
        validateAuthor(author);
        validatePageCount(pageCount);
        validatePublicationDate(publicationDate);

        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.pageCount = pageCount;
        this.publicationDate = publicationDate;
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Book title cannot be empty.");
        }
    }

    private static void validateISBN(ISBN isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN cannot be null.");
        }
    }

    private static void validateAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Book author cannot be empty.");
        }
    }

    private static void validatePageCount(int pageCount) {
        if (pageCount <= 0) {
            throw new IllegalArgumentException("Page count must be greater than zero.");
        }
    }

    private static void validatePublicationDate(LocalDate publicationDate) {
        if (publicationDate == null || publicationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Publication date cannot be null or in the future.");
        }
    }

    public BookId getId() { return id; }
    public String getTitle() { return title; }
    public ISBN getIsbn() { return isbn; }
    public String getAuthor() { return author; }
    public int getPageCount() { return pageCount; }
    public LocalDate getPublicationDate() { return publicationDate; }
}