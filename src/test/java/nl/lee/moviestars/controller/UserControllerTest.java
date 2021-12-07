package nl.lee.moviestars.controller;

import nl.lee.moviestars.model.Authority;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.model.User;
import nl.lee.moviestars.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(UserController.class)
@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Configuration
    @EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
    static class ContextConfiguration {
    }


    @Test
    public void getAllUsersTest() throws Exception {
        ArrayList<User> users = new ArrayList<User>();
        User user1 = new User();
        user1.setUsername("Frank");
        users.add(user1);

        Mockito.when(userService.getUsers())
                .thenReturn(users);

        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("Frank"))
                .andExpect(status().isOk());
    }


    @Test
    public void getUserTest() throws Exception {
        User user = new User();
        user.setUsername("Frank");

        Mockito
                .when(userService.getUser("Frank"))
                .thenReturn(Optional.of(user));

        mvc.perform(get("/users/Frank").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("username").value("Frank"))
                .andExpect(status().isOk());
    }


    @Test
    public void getAllUserMoviesTest() throws Exception {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        User user1 = new User();
        user1.setUsername("Frank");
        Movie movie = new Movie("mooie film", "action");
        movies.add(movie);
        user1.setMovies(movies);

        Mockito.when(userService.getMovies("Frank"))
                .thenReturn(user1.getMovies());

        mvc.perform(get("/users/Frank/movies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].movieTitle").value("mooie film"))
                .andExpect(status().isOk());
    }


    @Test
    public void getAllUserReviewsTest() throws Exception {
        ArrayList<Review> reviews = new ArrayList<Review>();
        User user1 = new User();
        user1.setUsername("Frank");
        Review review = new Review("hele mooie film", 6.5);
        reviews.add(review);

        Mockito.when(userService.getReviews("Frank"))
                .thenReturn(reviews);

        mvc.perform(get("/users/Frank/reviews").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].review").value("hele mooie film"))
                .andExpect(status().isOk());
    }


    @Test
    public void getAuthoritiesTest() throws Exception {
        ArrayList<Authority> authorities = new ArrayList<Authority>();
        User user1 = new User();
        user1.setUsername("Frank");
        user1.addAuthority("ROLE_admin");

        Mockito.when(userService.getAuthorities("Frank"))
                .thenReturn(user1.getAuthorities());

        mvc.perform(get("/users/Frank/authorities").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].authority").value("ROLE_admin"))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteUserTest() throws Exception {
        User user1 = new User();
        user1.setUsername("Frank");

        mvc.perform(delete("/users/Frank").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    public void deleteUserAuthoritiesTest() throws Exception {
        User user1 = new User();
        user1.setUsername("Frank");
        user1.addAuthority("ROLE_user");

        mvc.perform(delete("/users/Frank/authorities/ROLE_user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
