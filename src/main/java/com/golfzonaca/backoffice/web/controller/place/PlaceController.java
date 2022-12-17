package com.golfzonaca.backoffice.web.controller.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.type.AddInfoType;
import com.golfzonaca.backoffice.domain.type.DaysType;
import com.golfzonaca.backoffice.domain.type.RoomType;
import com.golfzonaca.backoffice.service.address.AddressService;
import com.golfzonaca.backoffice.service.company.CompanyService;
import com.golfzonaca.backoffice.service.place.PlaceService;
import com.golfzonaca.backoffice.web.controller.place.dto.AddressDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceAddDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceDetailDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceEditDto;
import com.golfzonaca.backoffice.web.controller.signin.dto.SignInDto;
import com.golfzonaca.backoffice.web.controller.typeconverter.DataTypeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/places")
@RequiredArgsConstructor
@Transactional
public class PlaceController {
    private final CompanyService companyService;
    private final PlaceService placeService;
    private final AddressService addressService;

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
    public String places(Model model, Authentication authentication) throws JsonProcessingException {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);

        model.addAttribute("places", company.getPlaceList());
        model.addAttribute("companyId", company.getId());
        model.addAttribute("companyLoginId", company.getLoginId());
        return "place/places";
    }

    @GetMapping("/{placeId}")
    public String placeDetail(@PathVariable Long placeId, Model model, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        List<Place> placeList = placeService.findByCompanyId(company.getId());
        Place place = placeService.findById(placeId);
        if (!placeList.contains(place)) {
            SignInDto signInDto = new SignInDto();
            model.addAttribute(signInDto);
            return "login/loginForm";
        } else {
            Map<String, Integer> roomQuantity = placeService.calculateRoomQuantity(place);
            PlaceDetailDto placeDetailDto = new PlaceDetailDto(place.getId(), place.getPlaceName(), place.getDescription(), DataTypeFormatter.stringToList(place.getOpenDays()), place.getPlaceStart().toString(), place.getPlaceEnd().toString(), DataTypeFormatter.stringToList(place.getPlaceAddInfo()), place.getAddress().getAddress(), place.getAddress().getPostalCode(), roomQuantity);
            model.addAttribute("DaysType", daysType());
            model.addAttribute("AddInfoType", addInfoType());
            model.addAttribute("RoomTypes", roomType());
            model.addAttribute("place", placeDetailDto);
            return "place/place";
        }
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("place", new PlaceAddDto());
        model.addAttribute("address", new AddressDto());
        return "place/addForm";
    }

    @Transactional
    @PostMapping("/add")
    public String addPlace(@ModelAttribute PlaceAddDto placeAddDto, AddressDto addressDto, RedirectAttributes redirectAttributes, Authentication authentication) throws IOException {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        Place place = placeService.save(placeAddDto, addressDto, company);
        redirectAttributes.addAttribute("id", place.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/places/{id}";
    }

    @GetMapping("/{placeId}/edit")
    public String editForm(@PathVariable Long placeId, Model model, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        List<Place> placeList = placeService.findByCompanyId(company.getId());
        Place findPlace = placeService.findById(placeId);
        if (!placeList.contains(findPlace)) {
            SignInDto signInDto = new SignInDto();
            model.addAttribute(signInDto);
            return "login/loginForm";
        } else {
            Map<String, Integer> roomQuantity = placeService.calculateRoomQuantity(findPlace);
            PlaceDetailDto placeDetailDto = new PlaceDetailDto(findPlace.getId(), findPlace.getPlaceName(), findPlace.getDescription(), DataTypeFormatter.stringToList(findPlace.getOpenDays()), findPlace.getPlaceStart().toString(), findPlace.getPlaceEnd().toString(), DataTypeFormatter.stringToList(findPlace.getPlaceAddInfo()), findPlace.getAddress().getAddress(), findPlace.getAddress().getPostalCode(), roomQuantity);
            model.addAttribute("place", placeDetailDto);
            return "place/editForm";
        }
    }

    @PostMapping("/{placeId}/edit")
    public String editPlace(@PathVariable Long placeId, @ModelAttribute PlaceEditDto placeEditDto, Model model, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        List<Place> placeList = placeService.findByCompanyId(company.getId());
        Place findPlace = placeService.findById(placeId);
        if (!placeList.contains(findPlace)) {
            SignInDto signInDto = new SignInDto();
            model.addAttribute(signInDto);
            return "login/loginForm";
        } else {
            placeService.update(placeId, placeEditDto);
            return "redirect:/places/{placeId}";
        }
    }


    @GetMapping("/{placeId}/delete")
    public String delete(@PathVariable Long placeId, Model model, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        Company company = companyService.findByLoginId(username);
        List<Place> placeList = placeService.findByCompanyId(company.getId());
        Place findPlace = placeService.findById(placeId);
        if (!placeList.contains(findPlace)) {
            SignInDto signInDto = new SignInDto();
            model.addAttribute(signInDto);
            return "login/loginForm";
        } else {
            addressService.delete(placeService.findById(placeId).getAddress());
            return "redirect:/places";
        }
    }
}