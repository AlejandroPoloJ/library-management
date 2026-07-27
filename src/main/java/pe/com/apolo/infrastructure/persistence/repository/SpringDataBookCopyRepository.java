package pe.com.apolo.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.apolo.domain.model.book.BookCopyStatus;
import pe.com.apolo.infrastructure.persistence.entity.BookCopyEntity;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataBookCopyRepository
        extends JpaRepository<BookCopyEntity, UUID> {

    Optional<BookCopyEntity> findById(UUID id);

    Optional<BookCopyEntity> findFirstByBookIdAndStatus(
            UUID bookId,
            BookCopyStatus status
    );
}