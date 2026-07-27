package pe.com.apolo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import pe.com.apolo.domain.model.book.BookCopyStatus;

import java.util.UUID;

@Entity
@Table(name = "book_copies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookCopyStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

}