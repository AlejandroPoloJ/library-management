package pe.com.apolo.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookEntity {
    @Id
    @Column(nullable = false)
    private UUID id;
    private String title;
    private String isbn;
    private String author;
    private int pageCount;
    private LocalDate publicationDate;
}
