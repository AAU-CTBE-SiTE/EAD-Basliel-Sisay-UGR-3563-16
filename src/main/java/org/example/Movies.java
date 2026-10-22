package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class Movies implements AutoCloseable {

    private final EntityManagerFactory emf;

    public Movies() {
        this("Movie");
    }

    public Movies(String persistenceUnitName) {
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public Movies(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Create (Save) a new movie.
     */
    public Movie save(Movie movie) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(movie);
            tx.commit();
            return movie;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to save movie: " + movie, e);
        } finally {
            em.close();
        }
    }

    /**
     * Read a movie by its ID.
     */
    public Optional<Movie> findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Movie movie = em.find(Movie.class, id);
            return Optional.ofNullable(movie);
        } finally {
            em.close();
        }
    }

    /**
     * Read all movies.
     */
    public List<Movie> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Update an existing movie.
     */
    public Movie update(Movie movie) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Movie merged = em.merge(movie);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to update movie: " + movie, e);
        } finally {
            em.close();
        }
    }

    /**
     * Delete a movie by ID.
     */
    public boolean delete(Long id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Movie movie = em.find(Movie.class, id);
            if (movie != null) {
                em.remove(movie);
                tx.commit();
                return true;
            } else {
                tx.rollback();
                return false;
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to delete movie with ID: " + id, e);
        } finally {
            em.close();
        }
    }

    @Override
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
