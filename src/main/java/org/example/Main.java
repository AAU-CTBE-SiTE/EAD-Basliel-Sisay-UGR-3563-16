package org.example;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try (Movies movieService = new Movies()) {
            // 1. CREATE
            Movie inception = new Movie("Inception", "Sci-Fi", LocalDate.of(2010, 7, 16));
            Movie interstellar = new Movie("Interstellar", "Sci-Fi", LocalDate.of(2014, 11, 7));
            Movie godfather = new Movie("The Godfather", "Crime", LocalDate.of(1972, 3, 24));
            Movie titanic = new Movie("Titanic", "Romance", LocalDate.of(1999,7, 16));

            movieService.save(inception);
            movieService.save(interstellar);
            movieService.save(godfather);
            movieService.save(titanic);
            System.out.println("Saved: " + titanic);
            System.out.println("Saved: " + inception);
            System.out.println("Saved: " + interstellar);
            System.out.println("Saved: " + godfather);

            // 2. READ (Find All)
            List<Movie> allMovies = movieService.findAll();
            allMovies.forEach(m -> System.out.println("Found: " + m));

            // 3. READ (Find by ID)
            movieService.findById(inception.getId()).ifPresent(m ->
                    System.out.println("Movie by ID " + inception.getId() + ": " + m)
            );

            // 4. UPDATE
            inception.setGenre("Sci-Fi / Action");
            movieService.update(inception);
            System.out.println("Updated: " + movieService.findById(inception.getId()).orElse(null));

            // 5. DELETE
            boolean deleted = movieService.delete(godfather.getId());
            System.out.println("Deleted movie with ID " + godfather.getId() + ": " + deleted);

            // 6. READ
            movieService.findAll().forEach(m -> System.out.println("Remaining: " + m));

        } catch (Exception e) {
            System.err.println("An error occurred during JPA operations: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
