package com.golfzonaca.backoffice.repository.roomstatus;

import com.golfzonaca.backoffice.domain.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class RoomStatusRepository {
    private final SpringJpaRoomStatusRepository jpaRepository;

    public void save(RoomStatus roomStatus) {
        jpaRepository.save(roomStatus);
    }

}
