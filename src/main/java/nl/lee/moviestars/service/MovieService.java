package nl.lee.moviestars.service;

import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Image;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.model.User;
import nl.lee.moviestars.repository.ImageRepository;
import nl.lee.moviestars.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private final MovieRepository movieRepository;

    @Autowired
    private final ImageRepository imageRepository;



    //constructor

    public MovieService(MovieRepository movieRepository, ImageRepository imageRepository) {
        this.movieRepository = movieRepository;
        this.imageRepository=imageRepository;
    }



    //searchMovies
    public Collection<Movie> getMovies(String movieTitle)
    {
        if (movieTitle.isEmpty()) {
            return movieRepository.findAll();
        }
        else {
            return movieRepository.findAllByMovieTitle(movieTitle);
        }


    }


    //create a new movie
    public long createMovie(Movie movie) {
        Movie newMovie = movieRepository.save(movie);
        return newMovie.getId();
    }

    //update an existing movie
    public void updateMovie(Long id, Movie newMovie) {
        if (!movieRepository.existsById(id)) throw new RecordNotFoundException();
        Movie movie = movieRepository.findById(id).get();
        movie.setMovieTitle(newMovie.getMovieTitle());
        movie.setMovieGenre(newMovie.getMovieGenre());
        movie.setMovieDescription(newMovie.getMovieDescription());
        movieRepository.save(movie);
    }

    //delete a movie by id
    public void deleteMovie(long id) {
        if (!movieRepository.existsById(id)) throw new RecordNotFoundException();
        movieRepository.deleteById(id);
    }

    //find a movie by id
    public Optional<Movie> getMovieById(long id) {
        if (!movieRepository.existsById(id)) throw new RecordNotFoundException();
        return movieRepository.findById(id);
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


/*    public void assignImageToMovie(Long id, Long imageId) {

        var optionalMovie = movieRepository.findById(id);

        var optionalImage = imageRepository.findById(imageId);

        if (optionalMovie.isPresent() && optionalImage.isPresent()) {

            var movie = optionalMovie.get();

            var image = optionalImage.get();

            movie.setImage(image);

            movieRepository.save(movie);


        } else {

            throw new RecordNotFoundException();

        }*/

    //update an existing movie
    public void assignImageToMovie(Long id, Long imageId) {
        if ((!movieRepository.existsById(id))||(!imageRepository.existsById(imageId))) throw new RecordNotFoundException();
        Movie movie = movieRepository.findById(id).get();
        Image image=imageRepository.findById(imageId).get();
        movie.setImage(image);
        movieRepository.save(movie);
    }














/*    //get the average rating of the movie
    public double getAverageRating(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            //list of review of the movie
            List<Review> reviews = movie.get().getReviews();
            //rating of movie from movieDataBaseApi else 0
            double apiRatingValue=movie.get().getMovieRating();
            //list to add all the ratings
            List<Double> ratings=new ArrayList<>();
            //total of ratings
            double sum=0;
            sum+=apiRatingValue;
            ratings.add(apiRatingValue);
            //loop over reviewslist and add each rating to the ratingslist
            for (int i = 0; i < reviews.size(); i++) {
                double number=(reviews.get(i).getRating());
                ratings.add(number);
                sum+=number;
            }
            //divide sum of reviews
            return sum/ratings.size();

        } else {
            throw new RecordNotFoundException();
        }
    }*/
}
