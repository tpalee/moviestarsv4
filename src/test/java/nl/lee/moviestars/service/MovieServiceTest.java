package nl.lee.moviestars.service;

import nl.lee.moviestars.MoviestarsApplicationTests;
import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.model.User;
import nl.lee.moviestars.repository.ImageRepository;
import nl.lee.moviestars.repository.MovieRepository;
import nl.lee.moviestars.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.mockito.Mockito.times;

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
    void createMovieTest() {
        Movie movie = new Movie("title", "Action");
        movie.setId(100);

        Mockito
                .when(movieRepository.save(movie))
                .thenReturn(movie);

        assertEquals(100, movieService.createMovie(movie));
        Mockito.verify(movieRepository, times(1)).save(movie);
    }


    @Test
    void deleteMovieTest() {
        Movie movie = new Movie("title", "Action");
        movie.setId(100);

        Mockito
                .when(movieRepository.existsById(100L))
                .thenReturn(true);

movieService.deleteMovie(100L);

        Mockito.verify(movieRepository, times(1)).deleteReviewsForMovie(100L);
        Mockito.verify(movieRepository, times(1)).deleteMovieById(100L);
    }


    @Test
    public void GetMoviesWhenThereAreMoviesTest() {
        List<Movie> movies = new ArrayList<>();
        Movie movie1 = new Movie("movie1", "action");
        movie1.setId(1L);
        movie1.setMovieRating(8.0);
        movies.add(movie1);

        Mockito
                .when(movieRepository.findAll())
                .thenReturn(movies);

        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie1));

        Collection<Movie> testList = movieService.getMovies();
        assertTrue(testList == movies);
    }


    @Test
    public void GetMoviesWhenThereAreNoMoviesTest() {
        List<Movie> movies = new ArrayList<>();

        Mockito
                .when(movieRepository.findAll())
                .thenReturn(movies);

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            movieService.getMovies();
        }, "RecordNotFoundException error was expected");
    }


    @Test
    public void GetAverageWhenMovieNotFoundThrowsExeptionTest() {
        Mockito
                .when(movieRepository.findById(3L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            movieService.getAverageRating(3L);
        }, "RecordNotFoundException error was expected");
    }


    @Test
    public void GetAverageWhenthereAreNoReviewsTest() {
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
    public void GetAverageWhenthereAreReviewsTest() {
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


}

