package com.golfzonaca.backoffice.repository.room;

import com.golfzonaca.backoffice.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class RoomRepository {

    private final JpaRoomRepository jpaRepository;
    private final QueryRoomRepository queryRepository;

    public Room save(Room room) {
        return jpaRepository.save(room);
    }

    public Optional<Room> findById(long id) {
        return jpaRepository.findById(id);
    }

    public List<Room> findRoomsByRoomType(String roomType) {
        return queryRepository.findRoomsByRoomType(roomType);
    }
    
    public void delete(Room room) {
        jpaRepository.delete(room);
    }
}
