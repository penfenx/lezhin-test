package com.lezhin.penfen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lezhin.penfen.controller.ReviewController;
import com.lezhin.penfen.request.ReviewRequest;
import com.lezhin.penfen.entity.Review;
import com.lezhin.penfen.service.ReviewService;
import com.lezhin.penfen.type.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ReviewController(reviewService)).build();
    }

    @Test
    public void testCreateReview() throws Exception {
        ReviewRequest request = new ReviewRequest();

        request.setUserId(1);
        request.setContentsId(1);
        request.setRating(Rating.LIKE);
        request.setComment("hahaha");

        Review expectedReview = new Review(); // Use other constructor or setters if available

        when(reviewService.createReview(Review.from(request))).thenReturn(expectedReview);

        mockMvc.perform(
                        post("/review")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(reviewService).createReview(Review.from(request));
    }
}