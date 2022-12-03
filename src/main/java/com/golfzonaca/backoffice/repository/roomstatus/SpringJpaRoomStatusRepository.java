package com.golfzonaca.backoffice.repository.roomstatus;

import com.golfzonaca.backoffice.domain.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SpringJpaRoomStatusRepository extends JpaRepository<RoomStatus, Long> {
}
