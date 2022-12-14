package com.golfzonaca.backoffice.service.reservation;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Reservation;
import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.repository.reservation.ReservationRepository;
import com.golfzonaca.backoffice.service.place.dto.ReservationDto;
import com.golfzonaca.backoffice.web.controller.reservation.dto.ReservationSearchCond;
import com.golfzonaca.backoffice.web.controller.typeconverter.RoomTypeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public void cancelReservation(Long reservationId) {
        Reservation findReservation = reservationRepository.findById(reservationId);
        reservationRepository.delete(findReservation);
    }

    public List<Reservation> findByResStartTime(Long roomId, LocalDate date, LocalTime time) {
        return reservationRepository.findByResStartTime(roomId, date, time);
    }

    public Map<Integer, ReservationDto> processReservationData(Place place, ReservationSearchCond searchData) {
        Map<Integer, ReservationDto> reservationDtoMap = new LinkedHashMap<>();
        List<Room> rooms = place.getRooms();
        for (Room room : rooms) {
            List<Reservation> reservations = reservationRepository.findByCondition(room.getId(), searchData);
            for (int i = 0; i < reservations.size(); i++) {
                Reservation reservation = reservations.get(i);
                reservationDtoMap.put(i, new ReservationDto(reservation.getId(), reservation.getUser().getUsername(), reservation.getUser().getPhoneNumber(), reservation.getUser().getEmail(), reservation.getRoom().getId(), RoomTypeFormatter.valueToDescription(reservation.getRoom().getRoomKind().getRoomType()), reservation.getResStartDate().toString() + " " + reservation.getResStartTime().toString(), reservation.getResEndDate().toString() + " " + reservation.getResEndTime().toString(), reservation.getResStatus().toString(), reservation.getFixStatus().toString()));
            }
        }
        return reservationDtoMap;
    }
}
