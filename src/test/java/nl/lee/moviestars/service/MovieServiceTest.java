package nl.lee.moviestars.service;

import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.model.User;
import nl.lee.moviestars.model.Image;
import nl.lee.moviestars.repository.ImageRepository;
import nl.lee.moviestars.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private MovieService movieService = new MovieService();


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
    public void GetMovieByIdWhenThereIsAMovieTest() {
        Movie movie1 = new Movie("movie1", "action");
        movie1.setId(1L);
        movie1.setMovieRating(8.0);

        Mockito
                .when(movieRepository.existsById(1L))
                .thenReturn(true);

        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie1));

        Optional<Movie> movie = movieService.getMovieById(1L);

        assertEquals(movie.get().getId(), 1L);
        Mockito.verify(movieRepository, times(3)).findById(1L);
    }


    @Test
    public void GetMovieByIdWhenThereIsNoMovieTest() {

        Mockito
                .when(movieRepository.existsById(1L))
                .thenReturn(false);

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            movieService.getMovieById(1L);
        }, "RecordNotFoundException error was expected");
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
    public void SearchMovieWhenMovieIsFoundTest() {
        List<Movie> movies = new ArrayList<>();
        List<Movie> movies2 = new ArrayList<>();
        Movie movie1 = new Movie("movie1", "action");
        movie1.setId(1L);
        movie1.setMovieRating(0.0);
        movies.add(movie1);

        Mockito
                .when(movieRepository.findAll())
                .thenReturn(movies);

        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie1));

        Mockito
                .when(movieRepository.findAllByMovieTitle("movie1"))
                .thenReturn(movies);

        movieService.searchMovie("movie1");

        assertTrue(movies.get(0).getMovieTitle() == "movie1");
        verify(movieRepository, times(1)).findAllByMovieTitle("movie1");
    }


    @Test
    public void SearchMovieWhenMovieNotFoundTest() {
        List<Movie> movies = new ArrayList<>();
        List<Movie> foundMovies = new ArrayList<>();
        Movie movie1 = new Movie("movie1", "action");
        movie1.setId(1L);
        movie1.setMovieRating(0.0);
        movies.add(movie1);

        Mockito
                .when(movieRepository.findAll())
                .thenReturn(movies);

        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie1));

        Mockito
                .when(movieRepository.findAllByMovieTitle("movie2"))
                .thenReturn(foundMovies);

        movieService.searchMovie("movie2");

        assertTrue(foundMovies.size() == 0);
        verify(movieRepository, times(1)).findAllByMovieTitle("movie2");
    }


    @Test
    void upDateMovieWhenNewDataIsAdded() {
        List<Movie> movies = new ArrayList<>();
        Movie movie1 = new Movie("movie1", "action");
        movie1.setId(1L);
        Movie movie2 = new Movie("movie2", "action");
        movies.add(movie1);

        Mockito
                .when((movieRepository.existsById(1L)))
                .thenReturn(true);

        Mockito
                .when((movieRepository.findById(1L)))
                .thenReturn(Optional.of(movie1));

        movieService.updateMovie(1L, movie2);

        assertTrue(movie1.getMovieTitle() == "movie2");
        Mockito.verify(movieRepository, times(1)).save(movie1);
    }


    @Test
    void NoupDateMovieWhenMovieNotFoundTest() {
        Movie movie2 = new Movie("movie2", "action");

        Mockito
                .when(movieRepository.existsById(1L))
                .thenReturn(false);

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            movieService.updateMovie(1L, movie2);
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


    @Test
    public void getReviewsWhenThereAreReviewsTest() {
        List<Review> reviews = new ArrayList<>();
        Movie movie1 = new Movie("movie1", "action");
        Review review1 = new Review("nice movie", 6.5);
        review1.setId(2L);
        reviews.add(review1);
        movie1.setReviews(reviews);
        movie1.setId(1L);

        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie1));

        movieService.getReviews(1L);

        assertTrue(movie1.getReviews().size() == 1);
    }


    @Test
    public void getReviewsWhenThereAreNoReviewsTest() {
        List<Review> reviews = new ArrayList<>();
        Movie movie1 = new Movie("movie1", "action");
        movie1.setReviews(reviews);
        movie1.setId(1L);

        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie1));

        movieService.getReviews(1L);

        assertTrue(movie1.getReviews().size() == 0);
    }


    @Test
    public void getReviewsWhenThereIsNoMovieTest() {

        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            movieService.getReviews(1L);
        }, "RecordNotFoundException error was expected");
    }


    @Test
    void assignImageToMovieWhenThereIsAnImageTest() {
        Movie movie1 = new Movie("movie1", "action");
        movie1.setId(1L);
        Image image1 = new Image();
        image1.setFileName("image");
        image1.setId(2L);

        Mockito
                .when(movieRepository.existsById(1L))
                .thenReturn(true);

        Mockito
                .when(imageRepository.existsById(2L))
                .thenReturn(true);

        Mockito
                .when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie1));

        Mockito
                .when(imageRepository.findById(2L))
                .thenReturn(Optional.of(image1));

        movieService.assignImageToMovie(1L, 2L);

        Mockito.verify(movieRepository, times(1)).save(movie1);
    }

}