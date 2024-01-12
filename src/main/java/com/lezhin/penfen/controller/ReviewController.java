package com.lezhin.penfen.controller;

import com.lezhin.penfen.request.ReviewRequest;
import com.lezhin.penfen.entity.Review;
import com.lezhin.penfen.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 사용자가 특정 작품에 대해 평가(좋아요/싫어요, 댓글)를 등록하는 API입니다.
     * 한 작품에 대해서 각 사용자는 한 번만 평가할 수 있으며, 댓글 등록은 선택사항입니다.
     *
     * @param reviewRequest 리뷰 데이터를 담고 있는 요청 객체
     * @return 생성되어 저장된 리뷰 객체
     */
    @PostMapping("")
    public Review createReview(@RequestBody ReviewRequest reviewRequest) {
        Review review = Review.from(reviewRequest);
        return reviewService.createReview(review);
    }

}
