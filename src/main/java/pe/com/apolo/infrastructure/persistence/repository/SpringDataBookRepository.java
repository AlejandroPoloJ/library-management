package pe.com.apolo.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.apolo.infrastructure.persistence.entity.BookEntity;

import java.util.UUID;

public interface SpringDataBookRepository
        extends JpaRepository<BookEntity, UUID> {
}
