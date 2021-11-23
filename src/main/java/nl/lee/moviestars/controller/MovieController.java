package nl.lee.moviestars.controller;

import nl.lee.moviestars.model.Image;
import nl.lee.moviestars.model.Movie;

import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.model.User;

import nl.lee.moviestars.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {


    @Autowired
    private MovieService movieService;



    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    //get all Movies
    @GetMapping(value = "")
    public ResponseEntity<Collection> getMovies(){
        return ResponseEntity.ok().body(movieService.getMovies());
    }


    //search for a movie by title
    @GetMapping(value = "/search")
    public ResponseEntity<Object> findMovieByMovieTitle(@RequestParam(name="movieTitle") String name){
        return ResponseEntity.ok().body(movieService.searchMovie(name));
    }


/*    @GetMapping(value = "")
    public ResponseEntity<Object> searchMovies(@RequestParam(name="movieTitle", defaultValue="") String name){
        return ResponseEntity.ok().body(movieService.getMovies(name));
    }*/

    //get movie by Id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Movie>> getMovie(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(movieService.getMovieById(id));
    }



    //create a new movie
    @PostMapping(value = "")
    public ResponseEntity<Object> createMovie(@RequestBody Movie movie) {
        long newId = movieService.createMovie(movie);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).header("Access-Control-Expose-Headers", "Location").build();
    }

    //update an existing movie
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) {
        movieService.updateMovie(id, movie);
        return ResponseEntity.noContent().build();
    }

    //delete movie
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable("id") long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }


    //get all reviews of a movie
    @GetMapping(value = "/{id}/reviews")
    public ResponseEntity getReviews(@PathVariable("id") int id) {
        Iterable<Review> movieReviews=movieService.getReviews(id);
        return ResponseEntity.ok(movieReviews);
    }



//update image in movie
    @PatchMapping(value = "/{id}/images/{imageId}")
    public ResponseEntity<Object> patchImage(@PathVariable("id") Long id, @PathVariable("imageId") Long imageId) {
         movieService.assignImageToMovie(id, imageId);
        return ResponseEntity.noContent().build();
    }

}
