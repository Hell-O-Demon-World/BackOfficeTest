package com.golfzonaca.backoffice.web.controller.reservation;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.service.place.PlaceService;
import com.golfzonaca.backoffice.service.place.dto.ReservationDto;
import com.golfzonaca.backoffice.service.reservation.ReservationService;
import com.golfzonaca.backoffice.web.controller.reservation.dto.ReservationSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final PlaceService placeService;
    private final ReservationService reservationService;

    @GetMapping("/{placeId}/reservations")
    public String reservations(@ModelAttribute("reservationSearch") ReservationSearchCond searchData, @PathVariable Long placeId, Model model) {
        Place place = placeService.findById(placeId);
        Map<Integer, ReservationDto> reservationDtoMap = reservationService.processReservationData(place, searchData);
        model.addAttribute("placeId", placeId);
        model.addAttribute("placeName", place.getPlaceName());
        model.addAttribute("reservations", reservationDtoMap);
        return "reservation/reservations";
    }

    @GetMapping("/{placeId}/reservations/{reservationId}/cancel")
    public String cancel(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return "redirect:/{placeId}/reservations";
    }
}
