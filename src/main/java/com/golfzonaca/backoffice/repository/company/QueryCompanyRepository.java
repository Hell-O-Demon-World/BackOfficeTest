package com.golfzonaca.backoffice.repository.company;

import com.golfzonaca.backoffice.domain.Company;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.golfzonaca.backoffice.domain.QCompany.company;

@Repository
@Transactional
public class QueryCompanyRepository {
    private final JPAQueryFactory query;

    public QueryCompanyRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    public Company findByLoginId(String loginId) {
        return query
                .selectFrom(company)
                .where(likeLoginId(loginId))
                .fetchOne();
    }

    private BooleanExpression likeLoginId(String loginId) {
        return company.loginId.like(loginId);
    }
}
