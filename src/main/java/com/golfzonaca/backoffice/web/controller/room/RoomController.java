package com.golfzonaca.backoffice.web.controller.room;

import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Reservation;
import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.service.company.CompanyService;
import com.golfzonaca.backoffice.service.place.PlaceService;
import com.golfzonaca.backoffice.service.reservation.ReservationService;
import com.golfzonaca.backoffice.service.room.RoomService;
import com.golfzonaca.backoffice.web.controller.room.dto.RoomDto;
import com.golfzonaca.backoffice.web.controller.signin.dto.SignInDto;
import com.golfzonaca.backoffice.web.controller.typeconverter.PresentReservationFormatter;
import com.golfzonaca.backoffice.web.controller.typeconverter.RoomStatusFormatter;
import com.golfzonaca.backoffice.web.controller.typeconverter.RoomTypeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RoomController {
    private final PlaceService placeService;
    private final RoomService roomService;
    private final CompanyService companyService;
    private final ReservationService reservationService;

    @GetMapping("/{placeId}/rooms")
    public String rooms(@PathVariable Long placeId, Model model, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        List<Place> placeList = placeService.findByCompanyId(company.getId());
        if (placeList.contains(placeId)) {
            SignInDto signInDto = new SignInDto();
            model.addAttribute(signInDto);
            return "login/loginForm";
        } else {
            Place place = placeService.findById(placeId);
            Map<Integer, RoomDto> rooms = processRoomData(place);
            model.addAttribute("placeName", place.getPlaceName());
            model.addAttribute("placeId", placeId);
            model.addAttribute("rooms", rooms);
            return "room/rooms";
        }
    }

    @GetMapping("/room/{roomId}/enable")
    public String enableRoom(@PathVariable Long roomId, RedirectAttributes redirectAttributes, Model model, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        List<Place> placeList = placeService.findByCompanyId(company.getId());
        Room findRoom = roomService.findById(roomId);
        for (Place place : placeList) {
            if (place.getRooms().contains(findRoom)) {
                Room room = roomService.updateStatus(roomId, true);
                redirectAttributes.addAttribute("placeId", room.getPlace().getId());
                return "redirect:/{placeId}/rooms";
            }
        }
        SignInDto signInDto = new SignInDto();
        model.addAttribute(signInDto);
        return "login/loginForm";
    }

    @GetMapping("/room/{roomId}/disable")
    public String disableRoom(@PathVariable Long roomId, RedirectAttributes redirectAttributes, Model model, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        List<Place> placeList = placeService.findByCompanyId(company.getId());
        Room findRoom = roomService.findById(roomId);
        for (Place place : placeList) {
            if (place.getRooms().contains(findRoom)) {
                Room room = roomService.updateStatus(roomId, false);
                redirectAttributes.addAttribute("placeId", room.getPlace().getId());
                return "redirect:/{placeId}/rooms";
            }
        }
        SignInDto signInDto = new SignInDto();
        model.addAttribute(signInDto);
        return "login/loginForm";
    }

    private Map<Integer, RoomDto> processRoomData(Place place) {
        Map<Integer, RoomDto> rooms = new LinkedHashMap<>();
        for (int i = 0; i < place.getRooms().size(); i++) {
            Room room = place.getRooms().get(i);
            List<Reservation> reservation = reservationService.findByResStartTime(room.getId(), LocalDate.now(), LocalTime.now());
            rooms.put(i, new RoomDto(room.getId().toString(), RoomTypeFormatter.valueToDescription(room.getRoomKind().getRoomType()), PresentReservationFormatter.booleanToString(reservation.isEmpty()), RoomStatusFormatter.booleanToString(room.getRoomStatus().getStatus())));
        }
        return rooms;
    }
}
