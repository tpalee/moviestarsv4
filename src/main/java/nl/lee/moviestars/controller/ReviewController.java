package nl.lee.moviestars.controller;
import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping(value = "/reviews")
    public ResponseEntity<Collection> getReviews() {
        return ResponseEntity.ok().body(reviewService.getReviews());
    }


    @GetMapping(value = "/reviews/badlanguage")
    public ResponseEntity<Collection> getReviewsHarmfullContent() {
        return ResponseEntity.ok().body(reviewService.getReviewsHarmfullContent());
    }


    @GetMapping(value = "/reviews/{id}")
    public ResponseEntity getReview(@PathVariable long id) {
        return ResponseEntity.ok().body(reviewService.findById(id));
    }


    @PostMapping(value = "/reviews")
    public ResponseEntity<Object> createReview(@RequestBody Review review) {
        long newId = reviewService.createReview(review);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).build();
    }


    @PutMapping(value = "/reviews/{id}")
    public ResponseEntity updateReview(@PathVariable long id, @RequestBody Review review) {
        reviewService.updateReview(id, review);
        return ResponseEntity.ok("Review is aangepast");
    }


    @PatchMapping(value = "/reviews/{id}/badlanguage")
    public ResponseEntity updateBadLanguage(@PathVariable long id) {
        reviewService.updateBadlanguage(id);
        return ResponseEntity.ok("Badlanguage aangepast");
    }


    @DeleteMapping(value = "/reviews/{id}")
    public ResponseEntity deleteReview(@PathVariable long id) {
        reviewService.deleteById(id);
        return ResponseEntity.ok("Review is verwijderd");
    }


}