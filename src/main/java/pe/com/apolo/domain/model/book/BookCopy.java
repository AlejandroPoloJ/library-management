package pe.com.apolo.domain.model.book;

import pe.com.apolo.domain.exception.BookAlreadyAvailableException;
import pe.com.apolo.domain.exception.BookNotAvailableException;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;
import pe.com.apolo.domain.model.book.valueobjects.BookId;

public class BookCopy {
    private final BookCopyId id;
    private BookCopyStatus status;

    public BookCopy(BookCopyId id, BookCopyStatus status) {
        validateId(id);
        initStatus(status);

        this.id = id;
    }

    private static void validateId(BookCopyId id) {
        if (id == null) {
            throw new IllegalArgumentException("BookCopyId cannot be null.");
        }
    }

    private void initStatus(BookCopyStatus status) {
        this.status = (status == null)
                ? BookCopyStatus.AVAILABLE
                : status;
    }

    public void loan() {
        if (this.status == BookCopyStatus.AVAILABLE) {
            this.status = BookCopyStatus.LOANED;
        } else {
            throw new BookNotAvailableException();
        }
    }

    public void returnBook() {
        if (this.status == BookCopyStatus.LOANED) {
            this.status = BookCopyStatus.AVAILABLE;
        } else {
            throw new BookAlreadyAvailableException();
        }
    }

    public boolean isAvailable() {
        return status == BookCopyStatus.AVAILABLE;
    }

    public static BookCopy create() {
        return new BookCopy(
                BookCopyId.generate(),
                BookCopyStatus.AVAILABLE
        );
    }

    public BookCopyId getId() {
        return id;
    }

    public BookCopyStatus getStatus() {
        return status;
    }
}
