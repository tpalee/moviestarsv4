package nl.lee.moviestars.service;

import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    //constructor
    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository=reviewRepository;
    }



    //get all the reviews
    public Iterable<Review> getReviews() {
        return reviewRepository.findAll();
    }


    //find all the reviews by id
    public Review findById(long nr) {
        Optional<Review> review = reviewRepository.findById(nr);
        if (review.isPresent()) {
            return review.get();
        }
        else {
            throw new RecordNotFoundException();
        }
    }

    //create a new review
    public long createReview(Review review) {
        Review newReview=reviewRepository.save(review);
        return newReview.getId();
    }

    //update an existing review
    public void updateReview(long id, Review newReview) {
        if (!reviewRepository.existsById(id)) throw new RecordNotFoundException();
        Review review = reviewRepository.findById(id).get();
        review.setReview(newReview.getReview());
        review.setReviewRating(newReview.getReviewRating());
        reviewRepository.save(review);
    }

    //delete a review by id
    public void deleteById(long id) {
        if (!reviewRepository.existsById(id)) throw new RecordNotFoundException();
        reviewRepository.deleteById(id);
    }

    //find a review by id
    public Optional<Review> getMovieById(long id) {
        if (reviewRepository.existsById(id)) throw new RecordNotFoundException();
        return reviewRepository.findById(id);
    }

}
