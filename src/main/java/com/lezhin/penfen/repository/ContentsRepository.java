package com.lezhin.penfen.repository;

import com.lezhin.penfen.dto.ContentsDislikes;
import com.lezhin.penfen.dto.ContentsLikes;
import com.lezhin.penfen.entity.*;
import com.lezhin.penfen.type.Rating;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lezhin.penfen.entity.QContents.contents;

@Repository
public class ContentsRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ContentsRepository(EntityManager em ) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public boolean existsById(int contentsId) {
        List<Contents> contentsList = queryFactory
                .selectFrom(contents)
                .where(contents.id.eq(contentsId))
                .fetch();
        return !contentsList.isEmpty();
    }

    public Contents findById(int id) {
        Contents contents = em.find(Contents.class, id);
        if (contents == null) {
            throw new EntityNotFoundException("Cannot find Contents with id: " + id);
        }
        return contents;
    }

    public List<User> findAllUsersByContentsId(int contentsId, Pageable pageable) {
        QUser user = QUser.user;
        QViewLog viewLog = QViewLog.viewLog;

        return queryFactory.selectFrom(user)
                .join(viewLog).on(viewLog.userId.eq(user.id))
                .where(viewLog.contentsId.eq(contentsId))
                .orderBy(viewLog.viewDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<ContentsLikes> findTop3ByMostLikes(int limit) {

        Map<Integer, Long> topRatedContentsByRating = getTopRatedContentsByRating(Rating.LIKE, limit);
        Set<Integer> integers = topRatedContentsByRating.keySet();
        List<Contents> contentList = getContentsByIds(integers);

        return contentList.stream()
                .map(content -> {
                    ContentsLikes contentsLikes = new ContentsLikes();
                    contentsLikes.setId(content.getId());
                    contentsLikes.setContentsName(content.getContentsName());
                    contentsLikes.setAuthor(content.getAuthor());
                    contentsLikes.setCoin(content.getCoin());
                    contentsLikes.setOpenDate(content.getOpenDate());
                    contentsLikes.setLikes(topRatedContentsByRating.getOrDefault(content.getId(), 0L).intValue());

                    return contentsLikes;
                })
                .sorted(Comparator.comparingInt(ContentsLikes::getLikes).reversed())
                .toList();
    }

    public List<ContentsDislikes> findTop3ByMostDislikes(int limit) {
        Map<Integer, Long> topRatedContentsByRating = getTopRatedContentsByRating(Rating.DISLIKE, limit);
        Set<Integer> integers = topRatedContentsByRating.keySet();
        List<Contents> contentList = getContentsByIds(integers);

        return contentList.stream()
                .map(content -> {
                    ContentsDislikes contentsDislikes = new ContentsDislikes();
                    contentsDislikes.setId(content.getId());
                    contentsDislikes.setContentsName(content.getContentsName());
                    contentsDislikes.setAuthor(content.getAuthor());
                    contentsDislikes.setCoin(content.getCoin());
                    contentsDislikes.setOpenDate(content.getOpenDate());
                    contentsDislikes.setDislikes(topRatedContentsByRating.getOrDefault(content.getId(), 0L).intValue());

                    return contentsDislikes;
                })
                .sorted(Comparator.comparingInt(ContentsDislikes::getDislikes).reversed())
                .toList();
    }

    private List<Contents> getContentsByIds(Set<Integer> keySet){
        return queryFactory
                .selectFrom(contents)
                .where(contents.id.in(keySet))
                .fetch();
    }

    private Map<Integer, Long> getTopRatedContentsByRating(Rating rating, int limit){
        QReview review = QReview.review;

        // 좋아요 수가 많은 순으로 3개의 contentsId와 좋아요 수를 가져온다.
        List<Tuple> topLikes = queryFactory.select(review.contentsId, review.rating.count())
                .from(review)
                .where(review.rating.eq(rating))
                .groupBy(review.contentsId)
                .orderBy(review.rating.count().desc())
                .limit(limit)
                .fetch();

        // Map<contentsId, 좋아요 수>로 변환
        return topLikes.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Integer.class),
                        tuple -> tuple.get(1, Long.class)));
    }

    @Transactional
    public void updateCoin(int contentsId, int coin) {
        JPAUpdateClause updateClause = new JPAUpdateClause(em, contents);

        long affectedRows = updateClause
                .where(contents.id.eq(contentsId))
                .set(contents.coin, coin)
                .execute();

        if (affectedRows == 0) {
            throw new EntityNotFoundException("Cannot find Contents with id: " + contentsId);
        }
    }

}
