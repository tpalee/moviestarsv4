package nl.lee.moviestars.service;

import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.repository.ImageRepository;
import nl.lee.moviestars.repository.MovieRepository;
import nl.lee.moviestars.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private MovieService movieService = new MovieService();

    @InjectMocks
    private ReviewService reviewService = new ReviewService();


    @Test
    void createReviewTest() {
        Review review = new Review("heel mooie", 7);
        review.setId(100);

        Mockito
                .when(reviewRepository.save(review))
                .thenReturn(review);

        assertEquals(100, reviewService.createReview(review));
        Mockito.verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void GetReviewsWhenThereAreReviewsTest() {
        List<Review> reviews = new ArrayList<>();

        Review review1 = new Review("mooi", 6);
        reviews.add(review1);
        review1.setId(1L);

        Review review2 = new Review("mooier", 7);
        review1.setId(2L);
        reviews.add(review2);

        Mockito
                .when(reviewRepository.findAll())
                .thenReturn(reviews);

        Collection totalReviews = reviewService.getReviews();

        assertTrue(totalReviews.size() == 2);
        Mockito.verify(reviewRepository, times(1)).findAll();
    }


    @Test
    public void testGetBadLanguageWhenthereisBadLanguage() {
        List<Review> reviews = new ArrayList<>();

        Review review1 = new Review("mooi", 6);
        review1.setBadLanguage(true);
        reviews.add(review1);
        review1.setId(1L);

        Mockito
                .when(reviewRepository.findReviewByBadLanguage(true))
                .thenReturn(reviews);

        Collection totalBadLanguage = reviewService.getReviewsHarmfullContent();


        assertTrue(totalBadLanguage.size() == 1);
        Mockito.verify(reviewRepository, times(1)).findReviewByBadLanguage(true);
    }


    @Test
    void findByIdShouldReturnTrueWhenReviewisFound() {
        Review review1 = new Review("mooi", 6);
        review1.setBadLanguage(true);
        review1.setId(1L);

        Mockito
                .when(reviewRepository.findById(1L))
                .thenReturn(Optional.of(review1));

        Review expected = reviewService.findById(1L);
        assertEquals(expected, review1);
    }

    @Test
    void findByIdShouldThrowErrorWhenReviewIsNotFound() {
        Mockito
                .when(reviewRepository.findById(3L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            reviewService.findById(3L);
        }, "RecordNotFoundException error was expected");
    }


    @Test
    void upDateReviewShouldSaveUpdatedReviewTest() {
        Review review1 = new Review("mooi", 6);
        review1.setBadLanguage(false);
        review1.setId(1L);

        Mockito
                .when((reviewRepository.existsById(1L)))
                .thenReturn(true);

        Mockito
                .when((reviewRepository.findById(1L)))
                .thenReturn(Optional.of(review1));


        review1.setReview("updated");
        review1.setReviewRating(9);

        reviewService.updateReview(1L, review1);
        reviewService.updateBadlanguage(1L);

        assertTrue(review1.isBadLanguage());
        Mockito.verify(reviewRepository,times(2)).save(review1);

        reviewRepository.deleteById(1L);
        Mockito.verify(reviewRepository,times(1)).deleteById(1L);
    }









}