package com.golfzonaca.backoffice.repository.reservation;

import com.golfzonaca.backoffice.domain.Reservation;
import com.golfzonaca.backoffice.web.controller.reservation.dto.ReservationSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.golfzonaca.backoffice.domain.QReservation.reservation;

@Repository
@Transactional
public class QueryReservationRepository {

    private final JPAQueryFactory query;

    public QueryReservationRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Reservation> findByPlaceId(Long roomId) {
        return query
                .selectFrom(reservation)
                .where(likeRoomId(roomId))
                .fetch();
    }

    public List<Reservation> findByPlaceIdAndPeriod(Long roomId, LocalDate startDate, LocalDate endDate) {
        return query
                .selectFrom(reservation)
                .where(likeRoomId(roomId), goeDate(startDate), loeDate(endDate))
                .fetch();
    }

    public Reservation findByIdAndPlaceId(Long roomId, Long reservationId) {
        return query
                .selectFrom(reservation)
                .where(likeRoomId(roomId), likeId(String.valueOf(reservationId)))
                .fetchFirst();
    }

    public List<Reservation> findByCondition(Long roomId, ReservationSearchCond data) {
        return query
                .selectFrom(reservation)
                .where(likeRoomId(roomId), (likeUserName(data.getSearchWord())).or(likeUserEmail(data.getSearchWord())))
                .fetch();
    }

    public List<Reservation> findByResStartTime(Long roomId, LocalDate date, LocalTime time) {
        return query
                .selectFrom(reservation)
                .where(likeRoomId(roomId), eqDate(date).and(goeTime(time)).or(afterDate(date)))
                .fetch();
    }

    private BooleanExpression likeRoomId(Long roomId) {
        if (roomId != null) {
            return reservation.room.id.eq(roomId);
        }
        return null;
    }

    private BooleanExpression likeId(String id) {
        if (StringUtils.hasText(id)) {
            return reservation.id.eq(Long.parseLong(id));
        }
        return null;
    }

    private BooleanExpression likeUserName(String userName) {
        if (StringUtils.hasText(userName)) {
            return reservation.user.username.like(userName);
        }
        return null;
    }

    private BooleanExpression likeUserEmail(String userEmail) {
        if (StringUtils.hasText(userEmail)) {
            return reservation.user.email.like(userEmail);
        }
        return null;
    }

    private BooleanExpression eqDate(LocalDate date) {
        return reservation.resStartDate.eq(date);
    }

    private BooleanExpression afterDate(LocalDate date) {
        return reservation.resStartDate.after(date);
    }

    private BooleanExpression goeDate(LocalDate date) {
        return reservation.resStartDate.goe(date);
    }

    private BooleanExpression loeDate(LocalDate date) {
        return reservation.resEndDate.loe(date);
    }

    private BooleanExpression goeTime(LocalTime time) {
        return reservation.resStartTime.goe(time);
    }
}
