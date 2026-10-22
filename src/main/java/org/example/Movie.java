package org.example;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "released_date")
    private LocalDate releasedDate;

    // Default constructor required by JPA
    public Movie() {
    }

    public Movie(String name, String genre, LocalDate releasedDate) {
        this.name = name;
        this.genre = genre;
        this.releasedDate = releasedDate;
    }

    public Movie(Long id, String name, String genre, LocalDate releasedDate) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.releasedDate = releasedDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", releasedDate=" + releasedDate +
                '}';
    }
}
