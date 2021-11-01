package nl.lee.moviestars.repository;
import nl.lee.moviestars.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    public Collection<Movie> findAllByMovieTitle(String movieTitle);
    public Optional<Movie> findMovieByMovieTitle(String movieTitle);

}
