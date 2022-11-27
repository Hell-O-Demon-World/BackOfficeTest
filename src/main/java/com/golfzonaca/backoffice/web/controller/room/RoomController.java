package com.golfzonaca.backoffice.web.controller.room;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.service.place.PlaceService;
import com.golfzonaca.backoffice.service.room.RoomService;
import com.golfzonaca.backoffice.web.controller.room.dto.RoomDto;
import com.golfzonaca.backoffice.web.controller.typeconverter.RoomStatusFormatter;
import com.golfzonaca.backoffice.web.controller.typeconverter.RoomTypeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RoomController {
    private final PlaceService placeService;
    private final RoomService roomService;

    @GetMapping("/{placeId}/rooms")
    public String rooms(@PathVariable Long placeId, Model model) {
        Place place = placeService.findById(placeId);
        Map<Integer, RoomDto> rooms = processRoomData(place);
        model.addAttribute("placeName", place.getPlaceName());
        model.addAttribute("rooms", rooms);
        return "room/rooms";
    }

    @GetMapping("/room/{roomId}/enable")
    public String enableRoom(@PathVariable Long roomId, RedirectAttributes redirectAttributes) {
        Room room = roomService.updateStatus(roomId, true);
        redirectAttributes.addAttribute("placeId", room.getPlace().getId());
        return "redirect:/{placeId}/rooms";
    }

    @GetMapping("/room/{roomId}/disable")
    public String disableRoom(@PathVariable Long roomId, RedirectAttributes redirectAttributes) {
        Room room = roomService.updateStatus(roomId, false);
        redirectAttributes.addAttribute("placeId", room.getPlace().getId());
        return "redirect:/{placeId}/rooms";
    }

    private Map<Integer, RoomDto> processRoomData(Place place) {
        Map<Integer, RoomDto> rooms = new LinkedHashMap<>();
        for (int i = 0; i < place.getRooms().size(); i++) {
            Room room = place.getRooms().get(i);
            rooms.put(i, new RoomDto(room.getId().toString(), RoomTypeFormatter.valueToDescription(room.getRoomKind().getRoomType()), "예약 없음", RoomStatusFormatter.booleanToString(room.getRoomStatus().getStatus())));
        }
        return rooms;
    }
}
