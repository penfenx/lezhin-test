package com.lezhin.penfen.entity;

import com.lezhin.penfen.request.ReviewRequest;
import com.lezhin.penfen.type.Rating;
import com.lezhin.penfen.util.StringUtil;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "review")
@IdClass(ReviewId.class)
public class Review {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "contents_id")
    private int contentsId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    private Rating rating;

    @Column(name = "comment")
    private String comment;



    public static Review from(ReviewRequest reviewRequest) {
        Review review = new Review();
        review.userId = reviewRequest.getUserId();
        review.contentsId = reviewRequest.getContentsId();
        review.rating = reviewRequest.getRating();
        review.comment = StringUtil.removeSpecialCharacters(reviewRequest.getComment()); // 특수문자 제거
        return review;
    }

}