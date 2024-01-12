package com.lezhin.penfen.repository;

import com.lezhin.penfen.entity.QViewLog;
import com.lezhin.penfen.entity.ViewLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ViewLogRepository{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ViewLogRepository(EntityManager em ) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    @Transactional
    public ViewLog save(ViewLog viewLog) {
        em.persist(viewLog);
        return viewLog;
    }

    public void deleteByUserId(int userId) {
        queryFactory
                .delete(QViewLog.viewLog)
                .where(QViewLog.viewLog.userId.eq(userId))
                .execute();
    }
}