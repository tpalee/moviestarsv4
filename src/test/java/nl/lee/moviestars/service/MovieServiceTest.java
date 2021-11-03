package nl.lee.moviestars.service;

import nl.lee.moviestars.MoviestarsApplicationTests;
import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.model.User;
import nl.lee.moviestars.repository.ImageRepository;
import nl.lee.moviestars.repository.MovieRepository;
import nl.lee.moviestars.repository.ReviewRepository;
import nl.lee.moviestars.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {


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

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testGetAverageWhenthereAreNoReviews() {
        User user1 = new User();
        Movie movie1 = new Movie("Terminator", "action", user1);
        Movie movie2 = new Movie("Terminator2", "action", user1);

        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie1));
        double expected = movieService.getAverageRating(1L);
        assertEquals(0.0, expected);
    }

    @Test
    public void testGetAverageWhenthereAreReviews() {
        User user1 = new User();
        Movie movie1 = new Movie("Terminator", "action", user1);
        Movie movie2 = new Movie("Terminator2", "action", user1);
        Review review1 = new Review("Nice movie", 8.5);
        Review review2 = new Review("Nice movie too", 7.5);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        movie1.setReviews(reviews);


        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie1));
        double expected = movieService.getAverageRating(1L);
        assertEquals(8.0, expected);
    }

/*    @Test(expected = NullPointerException.class)
    public void testGetAverageWhenMovieNotFoundThrowsExeption() {
        User user1 = new User();
        Movie movie1 = new Movie("Terminator", "action", user1);
        Movie movie2 = new Movie("Terminator2", "action", user1);
        Review review1 = new Review("Nice movie", 8.5);
        Review review2 = new Review("Nice movie too", 7.5);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        movie1.setReviews(reviews);


        Mockito
                .when(movieRepository.findById(3L))
                .thenThrow(new RecordNotFoundException());

        double expected = movieService.getAverageRating(3L);
    }*/


}
