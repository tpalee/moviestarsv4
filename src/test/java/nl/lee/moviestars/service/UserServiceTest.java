package nl.lee.moviestars.service;

import nl.lee.moviestars.MoviestarsApplication;
import nl.lee.moviestars.dto.request.UserPostRequest;
import nl.lee.moviestars.exceptions.UserNotFoundException;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.model.User;
import nl.lee.moviestars.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService = new UserService(userRepository, passwordEncoder);


    @Test
    void createUserTest() {
        Mockito.when(passwordEncoder.encode("password")).thenReturn("password");

        String username = "username";
        String email = "email";

        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setEmail(email);
        user.setEnabled(true);
        user.addAuthority("ROLE_USER");

        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserService userService = new UserService(userRepository, passwordEncoder);
        userService.createUser(new UserPostRequest(username, "password", email, new HashSet<>()));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }


    @Test
    void getUsersWhenThereAreUsersTest() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        userList.add(user1);
        userList.add(user2);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(userList);

        userService.getUsers();

        assertEquals(userService.getUsers().size(), 2);
    }


    @Test
    void getUsersByIdWhenThereIsUserTest() {
        User user1 = new User();
        user1.setUsername("Frank");

        Mockito
                .when(userRepository.findById("Frank"))
                .thenReturn(Optional.of(user1));

        userService.getUser("Frank");

        String expected = "Frank";

        assertEquals(userService.getUser("Frank").get().getUsername(), expected);
    }


    @Test
    void deleteUserWhenUserIsFoundTest() {
        User user1 = new User();
        user1.setUsername("Frank");

        Mockito
                .when(userRepository.existsById("Frank"))
                .thenReturn(true);

        userService.deleteUser("Frank");

        Mockito.verify(userRepository, times(1)).deleteById("Frank");
    }

    @Test
    void deleteUserWhenUserIsNotFoundTest() {


        Mockito
                .when(userRepository.existsById("Frank"))
                .thenReturn(false);

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser("Frank");
        }, "UserNotFoundException error was expected");
    }

    @Test
    void updateUserWhenUserIsFoundTest() {
        User user1 = new User();
        user1.setUsername("Frank");
        user1.setPassword("password1");
        user1.setEmail("Frank@frank.nl");
        user1.setEnabled(true);

        User newUser = new User();
        newUser.setPassword("password2");
        newUser.setEmail("user@user.nl");
        newUser.setEnabled(true);


        Mockito
                .when(userRepository.findById("Frank"))
                .thenReturn(Optional.of(user1));

        userService.updateUser("Frank", newUser);

        assertEquals(user1.getEmail(), "user@user.nl");
        Mockito.verify(userRepository, times(1)).save(user1);
    }

    @Test
    void updateUserWhenUserIsNotFoundTest() {
        User newUser = new User();
        newUser.setPassword("password2");
        newUser.setEmail("user@user.nl");
        newUser.setEnabled(true);

        Mockito
                .when(userRepository.findById("Frank"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser("Frank", newUser);
        }, "UserNotFoundException error was expected");
    }


    @Test
    void getAuthorityWhenUserIsFoundTest() {
        User user1 = new User();
        user1.setUsername("Frank");
        user1.setEnabled(true);
        user1.addAuthority("ROLE_admin");

        Mockito
                .when(userRepository.findById("Frank"))
                .thenReturn(Optional.of(user1));

        userService.getAuthorities("Frank");

        assertEquals(user1.getAuthorities().size(), 1);
    }


    @Test
    void AddAuthorityWhenUserIsFoundTest() {
        User user1 = new User();
        user1.setUsername("Frank");
        user1.setEnabled(true);

        Mockito
                .when(userRepository.findById("Frank"))
                .thenReturn(Optional.of(user1));

        userService.addAuthority("Frank", "ROLE_admin");

        assertEquals(user1.getAuthorities().size(), 1);
        Mockito.verify(userRepository, times(1)).save(user1);
    }


    @Test
    void ThrowErrorWhenUserIsNotFoundTest() {

        Mockito
                .when(userRepository.findById("Frank"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.addAuthority("Frank", "ROLE_user");
        }, "UserNotFoundException error was expected");

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.removeAuthority("Frank", "ROLE_user");
        }, "UserNotFoundException error was expected");

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getMovies("Frank");
        }, "UserNotFoundException error was expected");

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getReviews("Frank");
        }, "UserNotFoundException error was expected");
    }

    @Test
    void RemoveAuthorityWhenUserIsFoundTest() {
        User user1 = new User();
        user1.setUsername("Frank");
        user1.setEnabled(true);
        user1.addAuthority("ROLE_user");

        Mockito
                .when(userRepository.findById("Frank"))
                .thenReturn(Optional.of(user1));

        userService.removeAuthority("Frank", "ROLE_user");

        assertEquals(user1.getAuthorities().size(), 0);
        Mockito.verify(userRepository, times(1)).save(user1);
    }

    @Test
    void getMoviesGetReviewsWhenThereAreMoviesOrReviews() {
        List<Movie> movies = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("Frank");
        user1.setEnabled(true);
        Movie movie1 = new Movie("movie1", "action");
        movies.add(movie1);
        user1.setMovies(movies);
        Review review1 = new Review("nice", 8.5);
        reviews.add(review1);
        user1.setReviews(reviews);

        Mockito.when(userRepository.findById("Frank"))
                .thenReturn(Optional.of(user1));

        userService.getMovies("Frank");
        userService.getReviews("Frank");

        assertEquals(user1.getMovies().size(), 1);
        assertEquals(user1.getReviews().size(), 1);
    }


}