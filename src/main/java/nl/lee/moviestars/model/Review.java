package nl.lee.moviestars.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "review", nullable = false, length = 2024)
    private String review;

    @Column(name = "review_rating",nullable = false)
    private Double reviewRating;

    @Column(name = "bad_language")
    private boolean badLanguage=false;

    @Column(name="reviewer")
    private String reviewer;



    @ManyToOne
    @JoinColumn(name="username", nullable = false)
   @JsonBackReference(value="user-review")
    User user;


    @ManyToOne
    @JoinColumn(name="movie_id", nullable = false)
    @JsonBackReference(value="movie-review")
    Movie movie;



    public Review() {
    }

    public Review(String review, double reviewRating) {
        this.review = review;
        this.reviewRating = reviewRating;
    }


    //////////////////////
    //GETTERS AN SETTERS//
    //////////////////////


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(double reviewRating) {
        this.reviewRating = reviewRating;
    }

    public boolean isBadLanguage() {
        return badLanguage;
    }

    public void setBadLanguage(boolean badLanguage) {
        this.badLanguage = badLanguage;
    }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getReviewer() { return reviewer; }

    public void setReviewer(String reviewer) { this.reviewer = reviewer; }
}
