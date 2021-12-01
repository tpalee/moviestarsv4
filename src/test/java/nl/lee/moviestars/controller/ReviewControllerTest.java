package nl.lee.moviestars.controller;

import nl.lee.moviestars.model.Review;
import nl.lee.moviestars.repository.ReviewRepository;
import nl.lee.moviestars.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.ArrayList;

@Import(ReviewController.class)
@RunWith(SpringRunner.class)
@WebMvcTest(value = ReviewController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;


    @Configuration
    @EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
    static class ContextConfiguration {
    }

    @Test
    public void getAllReviewsTest() throws Exception {
        ArrayList<Review> reviews = new ArrayList<Review>();
        reviews.add(new Review("Mooie film", 6.0));

        Mockito.when(reviewService.getReviews())
                .thenReturn(reviews);

        mvc.perform(get("/reviews").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].review").value("Mooie film"))
                .andExpect(status().isOk());
    }


    @Test
    public void getReviewsByIDTest() throws Exception {
        Review review = new Review("Mooie film", 6.0);
        review.setId(1L);

        Mockito
                .when(reviewService.findById(1L))
                .thenReturn(review);

        mvc.perform(get("/reviews/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("review").value("Mooie film"))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteReviewsByIDTest() throws Exception {
        Review review = new Review("Mooie film", 6.0);
        review.setId(1L);

        Mockito
                .when(reviewRepository.existsById(1L))
                .thenReturn(true);

        mvc.perform(delete("/reviews/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
