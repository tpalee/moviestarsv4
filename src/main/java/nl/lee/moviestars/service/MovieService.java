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
import javax.transaction.Transactional;
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


    public Collection<Movie> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) throw new RecordNotFoundException();
        for (int i = 0; i < movies.size(); i++) {
            Movie newmovie = movies.get(i);
            Long id = newmovie.getId();
            newmovie.setMovieRating(getAverageRating(id));
        }
        return movies;
    }


    public Optional<Movie> getMovieById(long id) {
        if (!movieRepository.existsById(id)) throw new RecordNotFoundException();
        Movie movie = movieRepository.findById(id).get();
        movie.setMovieRating(getAverageRating(id));
        return movieRepository.findById(id);
    }


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


    public long createMovie(Movie movie) {
        if (movieRepository.findMovieByMovieTitle(movie.getMovieTitle()).isPresent()) {
            throw new MovieAlreadyExistsException();
        } else {
            Movie newMovie = movieRepository.save(movie);
            return newMovie.getId();
        }
    }


    public long updateMovie(Long id, Movie newMovie) {
        if (!movieRepository.existsById(id)) throw new RecordNotFoundException();
        Movie movie = movieRepository.findById(id).get();
        movie.setMovieTitle(newMovie.getMovieTitle());
        movie.setMovieGenre(newMovie.getMovieGenre());
        movie.setMovieDescription(newMovie.getMovieDescription());
        movieRepository.save(movie);
        return movie.getId();
    }


    /* The standard 'movieRepository.deleteById(id)' doesn't work properly;
    No errors are shown in the console, but the deletion of the movie fails
    A workaround is created in the movieRepository*/
    @Transactional
    public void deleteMovie(long id) {
        if (!movieRepository.existsById(id)) throw new RecordNotFoundException();
        movieRepository.deleteReviewsForMovie(id);
        movieRepository.deleteMovieById(id);
    }


    public Iterable<Review> getReviews(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return movie.get().getReviews();
        } else {
            throw new RecordNotFoundException();
        }
    }


    public void assignImageToMovie(Long id, Long imageId) {
        if ((!movieRepository.existsById(id)) || (!imageRepository.existsById(imageId)))
            throw new RecordNotFoundException();
        Movie movie = movieRepository.findById(id).get();
        Image image = imageRepository.findById(imageId).get();
        movie.setImage(image);
        movieRepository.save(movie);
    }


    public double getAverageRating(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            List<Review> reviews = movie.get().getReviews();
            if (reviews.size() < 1) {
                return 0.0;
            }
            List<Double> ratings = new ArrayList<>();
            Double sum = 0.0;
            for (int i = 0; i < reviews.size(); i++) {
                Double number = (reviews.get(i).getReviewRating());
                ratings.add(number);
                sum += number;
            }
            return Math.round((sum / ratings.size()) * 100.0) / 100.0;
        } else {
            throw new RecordNotFoundException();
        }
    }
}
