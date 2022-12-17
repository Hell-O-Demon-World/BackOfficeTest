package com.golfzonaca.backoffice.web.controller.reservation;

import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.service.company.CompanyService;
import com.golfzonaca.backoffice.service.place.PlaceService;
import com.golfzonaca.backoffice.service.place.dto.ReservationDto;
import com.golfzonaca.backoffice.service.reservation.ReservationService;
import com.golfzonaca.backoffice.web.controller.reservation.dto.ReservationSearchCond;
import com.golfzonaca.backoffice.web.controller.signin.dto.SignInDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final PlaceService placeService;
    private final ReservationService reservationService;
    private final CompanyService companyService;

    @GetMapping("/{placeId}/reservations")
    public String reservations(@ModelAttribute("reservationSearch") ReservationSearchCond searchData, @PathVariable Long placeId, Model model, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        List<Place> placeList = placeService.findByCompanyId(company.getId());
        Place place = placeService.findById(placeId);
        if (!placeList.contains(place)) {
            SignInDto signInDto = new SignInDto();
            model.addAttribute(signInDto);
            return "login/loginForm";
        } else {
            Map<Integer, ReservationDto> reservationDtoMap = reservationService.processReservationData(place, searchData);
            model.addAttribute("placeId", placeId);
            model.addAttribute("placeName", place.getPlaceName());
            model.addAttribute("reservations", reservationDtoMap);
            return "reservation/reservations";
        }
    }

    @GetMapping("/{placeId}/reservations/{reservationId}/cancel")
    public String cancel(@PathVariable Long placeId, @PathVariable Long reservationId, Model model, Authentication
            authentication) {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        List<Place> placeList = placeService.findByCompanyId(company.getId());
        Place place = placeService.findById(placeId);
        if (!placeList.contains(place)) {
            SignInDto signInDto = new SignInDto();
            model.addAttribute(signInDto);
            return "login/loginForm";
        } else {
            reservationService.cancelReservation(reservationId);
            return "redirect:/{placeId}/reservations";
        }
    }
}
