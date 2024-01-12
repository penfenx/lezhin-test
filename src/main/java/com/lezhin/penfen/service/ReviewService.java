package com.lezhin.penfen.service;

import com.lezhin.penfen.entity.Review;
import com.lezhin.penfen.entity.ReviewId;
import com.lezhin.penfen.repository.ContentsRepository;
import com.lezhin.penfen.repository.ReviewRepository;
import com.lezhin.penfen.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ContentsRepository contentsRepository;

    public Review createReview(Review review) {

        if (!userRepository.existsById(review.getUserId())) {
            throw new IllegalArgumentException("User does not exist!");
        }

        if (!contentsRepository.existsById(review.getContentsId())) {
            throw new IllegalArgumentException("Contents does not exist!");
        }

        ReviewId reviewId = new ReviewId(review.getUserId(), review.getContentsId());

        if (reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException("A review for this content by this user already exists!");
        }

        return reviewRepository.save(review);
    }

}
