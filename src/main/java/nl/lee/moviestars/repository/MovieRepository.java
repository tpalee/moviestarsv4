package nl.lee.moviestars.repository;

import nl.lee.moviestars.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    public Collection<Movie> findAllByMovieTitle(String movieTitle);
    public Optional<Movie> findMovieByMovieTitle(String movieTitle);

    @Modifying
    @Query(
            value = "delete from reviews where movie_id = ?1",
            nativeQuery = true)
    void deleteReviewsForMovie(Long id);

    @Modifying
    @Query(
            value = "delete from movies where movie_id = ?1",
            nativeQuery = true)
    void deleteMovieById(Long id);

}
