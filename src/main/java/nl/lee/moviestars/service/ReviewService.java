package nl.lee.moviestars.service;

import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    //get all the reviews
    public Collection<Review> getReviews() {
        return reviewRepository.findAll();
    }

    public Collection<Review> getReviewsHarmfullContent() {
       return reviewRepository.findReviewByBadLanguage(true);
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


    public void updateBadlanguage(long id) {
        if (!reviewRepository.existsById(id)) throw new RecordNotFoundException();
        Review review = reviewRepository.findById(id).get();
        boolean badLanguage=review.isBadLanguage();
        review.setBadLanguage(!badLanguage);
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
