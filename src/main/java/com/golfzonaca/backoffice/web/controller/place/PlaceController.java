package com.golfzonaca.backoffice.web.controller.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golfzonaca.backoffice.auth.token.JwtRepository;
import com.golfzonaca.backoffice.domain.Address;
import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.RatePoint;
import com.golfzonaca.backoffice.domain.type.AddInfoType;
import com.golfzonaca.backoffice.domain.type.DaysType;
import com.golfzonaca.backoffice.domain.type.RoomType;
import com.golfzonaca.backoffice.service.address.AddressService;
import com.golfzonaca.backoffice.service.company.CompanyService;
import com.golfzonaca.backoffice.service.place.PlaceService;
import com.golfzonaca.backoffice.service.ratepoint.RatePointService;
import com.golfzonaca.backoffice.service.room.RoomService;
import com.golfzonaca.backoffice.web.controller.place.dto.AddressDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceAddDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceDetailDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceEditDto;
import com.golfzonaca.backoffice.web.controller.typeconverter.DataTypeFormatter;
import com.golfzonaca.backoffice.web.controller.typeconverter.TimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    private final JwtRepository jwtRepository;
    private final CompanyService companyService;
    private final PlaceService placeService;
    private final RoomService roomService;
    private final AddressService addressService;
    private final RatePointService ratePointService;

    @ModelAttribute("DaysType")
    public DaysType[] daysType() {
        return DaysType.values();
    }

    @ModelAttribute("AddInfoType")
    public AddInfoType[] addInfoType() {
        return AddInfoType.values();
    }

    @ModelAttribute("RoomTypes")
    public RoomType[] roomType() {
        return RoomType.values();
    }

    @GetMapping
    public String places(Model model) throws JsonProcessingException {
        Company company = companyService.findById(jwtRepository.getUserId());
        model.addAttribute("places", company.getPlaceList());
        model.addAttribute("companyLoginId", company.getLoginId());
        return "place/places";
    }

    @GetMapping("/{placeId}")
    public String placeDetail(@PathVariable Long placeId, Model model) {
        Place place = placeService.findById(placeId);
        Map<String, Integer> roomQuantity = placeService.calculateRoomQuantity(place);

        PlaceDetailDto placeDetailDto = new PlaceDetailDto(place.getId(), place.getPlaceName(), place.getDescription(), DataTypeFormatter.stringToList(place.getOpenDays()), place.getPlaceStart().toString(), place.getPlaceEnd().toString(), DataTypeFormatter.stringToList(place.getPlaceAddInfo()), place.getAddress().getAddress(), place.getAddress().getPostalCode(), roomQuantity);
        model.addAttribute("DaysType", daysType());
        model.addAttribute("AddInfoType", addInfoType());
        model.addAttribute("RoomTypes", roomType());
        model.addAttribute("place", placeDetailDto);
        return "place/place";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("place", new PlaceAddDto());
        model.addAttribute("address", new AddressDto());
        return "place/addForm";
    }

    @Transactional
    @PostMapping("/add")
    public String addPlace(@ModelAttribute PlaceAddDto placeAddDto, AddressDto addressDto, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        Company company = companyService.findById(jwtRepository.getUserId());
        Address address = addressService.save(new Address(addressDto.getAddress(), addressDto.getPostalCode()));
        RatePoint ratePoint = ratePointService.save(new RatePoint(0F));
        Place place = placeService.save(new Place(company, ratePoint, placeAddDto.getPlaceName(), placeAddDto.getPlaceDescription(), DataTypeFormatter.listToString(placeAddDto.getPlaceOpenDays()), TimeFormatter.toLocalTime(placeAddDto.getPlaceStart()), TimeFormatter.toLocalTime(placeAddDto.getPlaceEnd()), DataTypeFormatter.listToString(placeAddDto.getPlaceAddInfo()), address));
        roomService.save(place, placeAddDto.getRoomQuantity());

        redirectAttributes.addAttribute("id", place.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/places/{id}";
    }

    @GetMapping("/{placeId}/edit")
    public String editForm(@PathVariable Long placeId, Model model) {
        Place place = placeService.findById(placeId);
        Map<String, Integer> roomQuantity = placeService.calculateRoomQuantity(place);
        PlaceDetailDto placeDetailDto = new PlaceDetailDto(place.getId(), place.getPlaceName(), place.getDescription(), DataTypeFormatter.stringToList(place.getOpenDays()), place.getPlaceStart().toString(), place.getPlaceEnd().toString(), DataTypeFormatter.stringToList(place.getPlaceAddInfo()), place.getAddress().getAddress(), place.getAddress().getPostalCode(), roomQuantity);
        model.addAttribute("place", placeDetailDto);
        return "place/editForm";
    }

    @PostMapping("/{placeId}/edit")
    public String editPlace(@PathVariable Long placeId, @ModelAttribute PlaceEditDto placeEditDto) {
        placeService.update(placeService.findById(placeId), placeEditDto);
        return "redirect:/places/{placeId}";
    }


    @GetMapping("/{placeId}/delete")
    public String delete(@PathVariable Long placeId) {
        addressService.delete(placeService.findById(placeId).getAddress());
        return "redirect:/places";
    }

    @GetMapping("/sign-out")
    public String signOut() {
        jwtRepository.clearAll();
        return "redirect:/sign-in";
    }
}