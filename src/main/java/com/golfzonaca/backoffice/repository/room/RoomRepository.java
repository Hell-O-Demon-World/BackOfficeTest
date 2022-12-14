package com.golfzonaca.backoffice.repository.room;

import com.golfzonaca.backoffice.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Transactional
@RequiredArgsConstructor
public class RoomRepository {

    private final JpaRoomRepository jpaRepository;
    private final QueryRoomRepository queryRepository;

    public Room save(Room room) {
        return jpaRepository.save(room);
    }

    public Room findById(long id) {
        return jpaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사무공간입니다."));
    }

    public List<Room> findRoomsByRoomType(String roomType) {
        return queryRepository.findRoomsByRoomType(roomType);
    }

    public void delete(Room room) {
        jpaRepository.delete(room);
    }

    public Room findByPlaceId(Long placeId) {
        return jpaRepository.findFirstByPlaceId(placeId).orElseThrow(()-> new NoSuchElementException("존재하지 않는 사무공간 입니다."));
    }
}
