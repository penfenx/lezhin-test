package com.lezhin.penfen.repository;

import com.lezhin.penfen.entity.Review;
import com.lezhin.penfen.entity.ReviewId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lezhin.penfen.entity.QReview.review;

@Repository
public class ReviewRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ReviewRepository(EntityManager em ) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public boolean existsById(ReviewId reviewId) {
        List<Review> reviewList = queryFactory
                .selectFrom(review)
                .where(review.userId.eq(reviewId.getUserId())
                        .and(review.contentsId.eq(reviewId.getContentsId())))
                .fetch();
        return !reviewList.isEmpty();
    }

    @Transactional
    public Review save(Review review) {
        em.persist(review);
        return review;
    }


    @Transactional
    public void deleteByUserId(int userId){
        queryFactory
                .delete(review)
                .where(review.userId.eq(userId))
                .execute();
    }
}
