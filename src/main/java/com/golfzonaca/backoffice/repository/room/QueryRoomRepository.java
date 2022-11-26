package com.golfzonaca.backoffice.repository.room;

import com.golfzonaca.backoffice.domain.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.golfzonaca.backoffice.domain.QRoom.room;

@Repository
@Transactional
public class QueryRoomRepository {

    private final JPAQueryFactory query;

    public QueryRoomRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Room> findRoomsByRoomType(String roomType) {
        return query
                .selectFrom(room)
                .where(room.roomKind.roomType.like(roomType))
                .fetch();
    }
}
