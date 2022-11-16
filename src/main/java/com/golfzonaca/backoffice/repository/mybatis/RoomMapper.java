package com.golfzonaca.backoffice.repository.mybatis;

import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.repository.dto.RoomStatusDto;
import com.golfzonaca.backoffice.repository.dto.RoomUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface RoomMapper {

    void save(Room room);

    void updateInfo(@Param("id") long id, @Param("updateParam") RoomUpdateDto updateParam);

    void updateStatus(@Param("id") long id, @Param("updateStatus") RoomStatusDto updateStatus);

    void deleteByPlaceId(long PlaceId);

    List<Room> findAllByPlaceId(long id);

    Optional<Room> findByRoomId(long id);

    List<Map<Integer, Integer>> countByRoomType(long placeId);
}
