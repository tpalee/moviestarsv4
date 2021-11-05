package nl.lee.moviestars.service;

import nl.lee.moviestars.exceptions.MovieAlreadyExistsException;
import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Image;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.repository.ImageRepository;
import nl.lee.moviestars.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ImageRepository imageRepository;


    //constructor

 /*   public MovieService(MovieRepository movieRepository, ImageRepository imageRepository) {
        this.movieRepository = movieRepository;
        this.imageRepository = imageRepository;
    }*/


    //getAllMovies
    public Collection<Movie> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) throw new RecordNotFoundException();
        for (int i = 0; i < movies.size(); i++) {
            Movie newmovie = movies.get(i);
            Long id = newmovie.getId();
            newmovie.setMovieRating(getAverageRating(id));
        }
        return movieRepository.findAll();
    }


    //find a movie by id
    public Optional<Movie> getMovieById(long id) {
        if (!movieRepository.existsById(id)) throw new RecordNotFoundException();
        Movie movie = movieRepository.findById(id).get();
        movie.setMovieRating(getAverageRating(id));
        return movieRepository.findById(id);
    }

    //search movies
    public Collection<Movie> searchMovie(String movieTitle) {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) throw new RecordNotFoundException();
        for (int i = 0; i < movies.size(); i++) {
            Movie newmovie = movies.get(i);
            Long id = newmovie.getId();
            newmovie.setMovieRating(getAverageRating(id));
        }
        return movieRepository.findAllByMovieTitle(movieTitle);
    }


    //create a new movie
    public long createMovie(Movie movie) {
        if (movieRepository.findMovieByMovieTitle(movie.getMovieTitle()).isPresent()) {
            throw new MovieAlreadyExistsException();
        } else {
            Movie newMovie = movieRepository.save(movie);
/*        Image image=imageRepository.findById(movie.getImageId()).get();
        newMovie.setImage(image);
        movieRepository.save(newMovie);*/
            return newMovie.getId();
        }
    }

    //update an existing movie
    public long updateMovie(Long id, Movie newMovie) {
        if (!movieRepository.existsById(id)) throw new RecordNotFoundException();
        Movie movie = movieRepository.findById(id).get();
        movie.setMovieTitle(newMovie.getMovieTitle());
        movie.setMovieGenre(newMovie.getMovieGenre());
        movie.setMovieDescription(newMovie.getMovieDescription());
        movieRepository.save(movie);
        return movie.getId();
    }

    //delete a movie by id
    public void deleteMovie(long id) {
        if (!movieRepository.existsById(id)) throw new RecordNotFoundException();
        movieRepository.deleteById(id);
    }


    //Get all the reviews of the movie_Id
    public Iterable<Review> getReviews(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return movie.get().getReviews();
        } else {
            throw new RecordNotFoundException();
        }
    }


    //update an existing movie
    public void assignImageToMovie(Long id, Long imageId) {
        if ((!movieRepository.existsById(id)) || (!imageRepository.existsById(imageId)))
            throw new RecordNotFoundException();
        Movie movie = movieRepository.findById(id).get();
        Image image = imageRepository.findById(imageId).get();
        movie.setImage(image);
        movieRepository.save(movie);
    }


    //get the average rating of the movie
    public double getAverageRating(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            List<Review> reviews = movie.get().getReviews();
            if (reviews.size() < 1) {
                return 0.0;
            }
            List<Double> ratings = new ArrayList<>();
            double sum = 0;
            for (int i = 0; i < reviews.size(); i++) {
                double number = (reviews.get(i).getReviewRating());
                ratings.add(number);
                sum += number;
            }
            return sum / ratings.size();
        } else {
            throw new RecordNotFoundException();
        }
    }
}
