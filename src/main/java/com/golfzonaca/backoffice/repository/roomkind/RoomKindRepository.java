package com.golfzonaca.backoffice.repository.roomkind;

import com.golfzonaca.backoffice.domain.RoomKind;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Repository
@Transactional
@RequiredArgsConstructor
public class RoomKindRepository {
    private final JpaRoomKindRepository jpaRepository;

    public RoomKind findById(long id) {
        return jpaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 방 종류입니다."));
    }
}
