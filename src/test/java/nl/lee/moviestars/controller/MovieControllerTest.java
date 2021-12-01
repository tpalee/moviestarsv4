package nl.lee.moviestars.controller;

import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.repository.MovieRepository;
import nl.lee.moviestars.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(MovieController.class)
@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @Configuration
    @EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
    static class ContextConfiguration {
    }


    @Test
    public void getAllMoviesTest() throws Exception {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie("Mooie film", "action"));

        Mockito.when(movieService.getMovies())
                .thenReturn(movies);

        mvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].movieTitle").value("Mooie film"))
                .andExpect(status().isOk());
    }


    @Test
    public void getMovieByIdTest() throws Exception {
        Movie movie = new Movie("Mooie film", "action");
        movie.setMovieRating(0.0);
        movie.setId(1L);

        Mockito
                .when(movieService.getMovieById(1L))
                .thenReturn(Optional.of(movie));

        mvc.perform(get("/movies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("movieTitle").value("Mooie film"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMovieByMovieTitleTest() throws Exception {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Movie movie = new Movie("Hollowman", "action");
        movie.setId(1L);
        movies.add(movie);

        Mockito
                .when(movieService.searchMovie("Hollowman"))
                .thenReturn(movies);

        mvc.perform(get("http://localhost:8080/movies/search/?movieTitle=Hollowman").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].movieTitle").value("Hollowman"))
                .andExpect(status().isOk());
    }


    @Test
    public void getreviewsOfMovieTest() throws Exception {
        ArrayList<Review> reviews = new ArrayList<Review>();
        Movie movie = new Movie("Hollowman", "action");
        movie.setId(1L);
        Review review = new Review("nice movie", 3.0);
        reviews.add(review);
        movie.setReviews(reviews);

        Mockito
                .when(movieService.getReviews(1L))
                .thenReturn(movie.getReviews());

        mvc.perform(get("/movies/1/reviews").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].review").value("nice movie"))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteMovieByIDTest() throws Exception {
        Movie movie = new Movie("Mooie film", "action");
        movie.setId(1L);

        Mockito
                .when(movieRepository.existsById(1L))
                .thenReturn(true);

        mvc.perform(delete("/movies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
