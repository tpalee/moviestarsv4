package nl.lee.moviestars.service;

import nl.lee.moviestars.dto.request.UserPostRequest;
import nl.lee.moviestars.model.User;
import nl.lee.moviestars.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void createUser() {
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
}
