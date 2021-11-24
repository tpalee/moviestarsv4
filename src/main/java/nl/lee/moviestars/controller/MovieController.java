package nl.lee.moviestars.controller;
import nl.lee.moviestars.model.Movie;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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



    @GetMapping(value = "")
    public ResponseEntity<Collection> getMovies(){
        return ResponseEntity.ok().body(movieService.getMovies());
    }


    @GetMapping(value = "/search")
    public ResponseEntity<Object> findMovieByMovieTitle(@RequestParam(name="movieTitle") String name){
        return ResponseEntity.ok().body(movieService.searchMovie(name));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Movie>> getMovie(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(movieService.getMovieById(id));
    }


    @PostMapping(value = "")
    public ResponseEntity<Object> createMovie(@RequestBody Movie movie) {
        long newId = movieService.createMovie(movie);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).header("Access-Control-Expose-Headers", "Location").build();
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) {
        movieService.updateMovie(id, movie);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable("id") long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping(value = "/{id}/reviews")
    public ResponseEntity getReviews(@PathVariable("id") int id) {
        Iterable<Review> movieReviews=movieService.getReviews(id);
        return ResponseEntity.ok(movieReviews);
    }


    @PatchMapping(value = "/{id}/images/{imageId}")
    public ResponseEntity<Object> patchImage(@PathVariable("id") Long id, @PathVariable("imageId") Long imageId) {
         movieService.assignImageToMovie(id, imageId);
        return ResponseEntity.noContent().build();
    }

}
