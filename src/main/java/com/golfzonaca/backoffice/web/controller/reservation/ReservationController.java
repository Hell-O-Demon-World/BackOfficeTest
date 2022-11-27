package com.golfzonaca.backoffice.web.controller.reservation;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Reservation;
import com.golfzonaca.backoffice.service.place.PlaceService;
import com.golfzonaca.backoffice.service.reservation.ReservationService;
import com.golfzonaca.backoffice.web.controller.reservation.dto.ReservationDto;
import com.golfzonaca.backoffice.web.controller.reservation.dto.ReservationSearchCond;
import com.golfzonaca.backoffice.web.controller.typeconverter.RoomTypeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
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
        Map<Integer, ReservationDto> reservationDtoMap = processReservationData(placeId, searchData);
        model.addAttribute("placeId", placeId);
        model.addAttribute("placeName", place.getPlaceName());
        model.addAttribute("reservations", reservationDtoMap);
        return "reservation/reservations";
    }

    @GetMapping("/{placeId}/reservations/{reservationId}/cancel")
    public String cancelReservation(@PathVariable Long placeId, @PathVariable Long reservationId, RedirectAttributes redirectAttributes) {
        reservationService.delete(placeId, reservationId);
        redirectAttributes.addAttribute("placeId", placeId);
        return "redirect:/{placeId}/reservations";
    }

    private Map<Integer, ReservationDto> processReservationData(Long placeId, ReservationSearchCond searchData) {
        Map<Integer, ReservationDto> reservationDtoMap = new LinkedHashMap<>();
        List<Reservation> reservations = reservationService.findByCondition(placeId, searchData);
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            reservationDtoMap.put(i, new ReservationDto(reservation.getId(), reservation.getUser().getUsername(), reservation.getUser().getPhoneNumber(), reservation.getUser().getEmail(), reservation.getRoom().getId(), RoomTypeFormatter.valueToDescription(reservation.getRoom().getRoomKind().getRoomType()), LocalDateTime.of(reservation.getResStartDate(), reservation.getResStartTime()), LocalDateTime.of(reservation.getResEndDate(), reservation.getResEndTime())));
        }
        return reservationDtoMap;
    }
}
