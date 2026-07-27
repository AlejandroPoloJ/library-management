package pe.com.apolo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
