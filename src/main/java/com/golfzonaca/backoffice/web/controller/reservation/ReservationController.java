package com.golfzonaca.backoffice.web.controller.reservation;

import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.service.place.PlaceService;
import com.golfzonaca.backoffice.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final PlaceService placeService;
    private final RoomService roomService;
//    private final ReservationService reservationService;

    @GetMapping("/{roomId}/reservations")
    public String reservations(@PathVariable Long roomId, Model model) {
        Room room = roomService.findById(roomId);
        room.getReservationList();

        model.addAttribute("roomType", roomInfo);
        model.addAttribute("rooms", rooms);
        return "reservation/reservations";
    }
}
