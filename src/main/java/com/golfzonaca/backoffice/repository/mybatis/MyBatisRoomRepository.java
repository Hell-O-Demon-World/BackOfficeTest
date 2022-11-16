package com.golfzonaca.backoffice.repository.mybatis;

import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.repository.RoomRepository;
import com.golfzonaca.backoffice.repository.dto.RoomStatusDto;
import com.golfzonaca.backoffice.repository.dto.RoomUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisRoomRepository implements RoomRepository {

    private final RoomMapper roomMapper;

    @Override
    public Room save(Room room) {
        roomMapper.save(room);
        return room;
    }

    @Override
    public void updateInfo(long id, RoomUpdateDto updateParam) {
        roomMapper.updateInfo(id, updateParam);
    }

    @Override
    public void updateStatus(long id, RoomStatusDto updateStatus) {
        roomMapper.updateStatus(id, updateStatus);
    }

    @Override
    public void deleteByPlaceId(long placeId) {
        roomMapper.deleteByPlaceId(placeId);
    }

    @Override
    public Optional<Room> findByRoomId(long id) {
        return roomMapper.findByRoomId(id);
    }

    @Override
    public List<Room> findAllByPlaceId(long id) {
        return roomMapper.findAllByPlaceId(id);
    }

    @Override
    public List<Map<Integer, Integer>> countByRoomType(long placeId) {
        return roomMapper.countByRoomType(placeId);
    }
}
