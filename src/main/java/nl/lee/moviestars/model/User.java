package nl.lee.moviestars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String email;


    @OneToMany(
            targetEntity = nl.lee.moviestars.model.Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<nl.lee.moviestars.model.Authority> authorities = new HashSet<>();


    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference(value = "user-movie")
    @JsonIgnore
    private List<Movie> movies = new ArrayList<>();


    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-review")
    List<Review> reviews = new ArrayList<>();


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<nl.lee.moviestars.model.Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<nl.lee.moviestars.model.Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(nl.lee.moviestars.model.Authority authority) {
        this.authorities.add(authority);
    }

    public void addAuthority(String authorityString) {
        this.authorities.add(new nl.lee.moviestars.model.Authority(this.username, authorityString));
    }

    public void removeAuthority(String authorityString) {
        this.authorities.removeIf(authority -> authority.getAuthority().equalsIgnoreCase(authorityString));
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }


    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }







}

