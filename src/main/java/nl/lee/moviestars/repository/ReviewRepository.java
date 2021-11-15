package nl.lee.moviestars.repository;
import nl.lee.moviestars.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
Collection<Review>findReviewByBadLanguage(Boolean value);
}
