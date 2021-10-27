package nl.lee.moviestars.repository;
import nl.lee.moviestars.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
   /* List<Review> findAllByMovie(Movie movie);*/

}
