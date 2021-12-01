package nl.lee.moviestars.service;

import nl.lee.moviestars.dto.request.UserPostRequest;
import nl.lee.moviestars.exceptions.*;
import nl.lee.moviestars.model.Authority;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.model.User;
import nl.lee.moviestars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {


    private UserRepository userRepository;
    PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Collection<User> getUsers() {
        return userRepository.findAll();
    }


    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }


    public String createUser(UserPostRequest userPostRequest) {
        if (userRepository.existsById(userPostRequest.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            try {
                String encryptedPassword = passwordEncoder.encode(userPostRequest.getPassword());
                User user = new User();
                user.setUsername(userPostRequest.getUsername());
                user.setPassword(encryptedPassword);
                user.setEmail(userPostRequest.getEmail());
                user.setEnabled(true);
                user.addAuthority("ROLE_USER");
                User newUser = userRepository.save(user);
                return newUser.getUsername();
            } catch (Exception ex) {
                throw new BadRequestException("Cannot create user.");
            }
        }
    }


    public void deleteUser(String username) {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
        } else {
            throw new UserNotFoundException(username);
        }
    }


    public void updateUser(String username, User newUser) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        } else {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setEmail(newUser.getEmail());
            user.setEnabled(newUser.isEnabled());
            userRepository.save(user);
        }
    }


    public Set<Authority> getAuthorities(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        } else {
            User user = userOptional.get();
            return user.getAuthorities();
        }
    }


    public void addAuthority(String username, String authorityString) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        } else {
            User user = userOptional.get();
            user.addAuthority(authorityString);
            userRepository.save(user);
        }
    }


    public void removeAuthority(String username, String authorityString) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        } else {
            User user = userOptional.get();
            user.removeAuthority(authorityString);
            userRepository.save(user);
        }
    }


    public Iterable<Movie> getMovies(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return user.get().getMovies();
        } else {
            throw new UserNotFoundException(username);
        }
    }


    public Iterable<Review> getReviews(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return user.get().getReviews();
        } else {
            throw new UserNotFoundException(username);
        }
    }


}