package nl.lee.moviestars.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private long id;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "movie_genre")
    private String movieGenre;

    @Column(name = "movie_description", length = 1024)
    private String movieDescription;

    @Column(name = "movie_rating")
    private Double movieRating = 0.0;

    @Column(name = "movie_poster")
    private String moviePoster;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @JsonBackReference(value = "user-movie")
    private User user;


    @OneToMany(mappedBy = "movie",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference(value = "movie-review")
    List<Review> reviews = new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @JsonManagedReference(value = "movie-image")
    private Image image;


    public Movie() {
    }


    public Movie(String movieTitle, String movieGenre) {
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
    }


    public Movie(String movieTitle, String movieGenre, User user) {
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public Double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(Double movieRating) {
        this.movieRating = movieRating;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getMoviePoster() { return moviePoster; }

    public void setMoviePoster(String moviePoster) { this.moviePoster = moviePoster; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
