package com.lezhin.penfen.repository;


import com.lezhin.penfen.entity.QContents;
import com.lezhin.penfen.entity.QUser;
import com.lezhin.penfen.entity.QViewLog;
import com.lezhin.penfen.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class UserRepository  {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public UserRepository(EntityManager em ) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    public boolean existsById(int userId) {
        List<User> userList = queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.id.eq(userId))
                .fetch();
        return !userList.isEmpty();
    }

    public User findById(int id) {
        return queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.id.eq(id))
                .fetchOne();
    }

    public List<User> findUsersViewedAdultContents(int days, int minViews, Pageable pageable) {
        QUser user = QUser.user;
        QViewLog viewLog = QViewLog.viewLog;
        QContents contents = QContents.contents;

        BooleanExpression isAdultContents = contents.isAdult.eq(true);
        BooleanExpression reviewedWithinDays = viewLog.viewDate.after(Date.from(LocalDateTime.now().minusDays(days).atZone(ZoneId.systemDefault()).toInstant()));

        return queryFactory.selectFrom(user)
                .join(viewLog).on(viewLog.userId.eq(user.id))
                .join(contents).on(viewLog.contentsId.eq(contents.id))
                .where(isAdultContents.and(reviewedWithinDays))
                .groupBy(user.id)
                .having(viewLog.count().goe(minViews))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<User> findRecentUsersWithAdultContentsView(int days, int minViews, Pageable pageable) {
        QUser user = QUser.user;
        QViewLog viewLog = QViewLog.viewLog;
        QContents contents = QContents.contents;

        BooleanExpression isAdultContents = contents.isAdult.eq(true);
        Timestamp daysAgo = Timestamp.valueOf(LocalDateTime.now().minusDays(days));
        BooleanExpression userRegisteredWithinDays = user.registerDate.after(daysAgo);
        BooleanExpression userViewedWithinDays = viewLog.viewDate.after(daysAgo);

        return queryFactory.selectFrom(user)
                .join(viewLog).on(viewLog.userId.eq(user.id))
                .join(contents).on(viewLog.contentsId.eq(contents.id))
                .where(isAdultContents.and(userRegisteredWithinDays).and(userViewedWithinDays))
                .groupBy(user.id)
                .having(viewLog.count().goe(minViews))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public void deleteById(int userId) {
        queryFactory
                .delete(QUser.user)
                .where(QUser.user.id.eq(userId))
                .execute();
    }
}
