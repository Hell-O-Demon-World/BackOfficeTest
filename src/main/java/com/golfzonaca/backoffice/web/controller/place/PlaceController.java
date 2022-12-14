package com.golfzonaca.backoffice.web.controller.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golfzonaca.backoffice.annotation.TokenUserId;
import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.domain.CompanyAccessToken;
import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.type.AddInfoType;
import com.golfzonaca.backoffice.domain.type.DaysType;
import com.golfzonaca.backoffice.domain.type.RoomType;
import com.golfzonaca.backoffice.repository.company.CompanyRepository;
import com.golfzonaca.backoffice.repository.company.CompanyTokenRepository;
import com.golfzonaca.backoffice.service.address.AddressService;
import com.golfzonaca.backoffice.service.company.CompanyService;
import com.golfzonaca.backoffice.service.place.PlaceService;
import com.golfzonaca.backoffice.web.controller.place.dto.AddressDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceAddDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceDetailDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceEditDto;
import com.golfzonaca.backoffice.web.controller.typeconverter.DataTypeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    private final CompanyService companyService;
    private final PlaceService placeService;
    private final AddressService addressService;
    private final CompanyTokenRepository companyTokenRepository;

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
    public String places(@TokenUserId Long userId, Model model) throws JsonProcessingException {
        Company company = companyService.findById(userId);
        CompanyAccessToken accessTokenByCompany = companyTokenRepository.findAccessTokenByCompany(company);
        model.addAttribute("accessToken", accessTokenByCompany);
        model.addAttribute("places", company.getPlaceList());
        model.addAttribute("companyId", company.getId());
        model.addAttribute("companyLoginId", company.getLoginId());
        return "place/places";
    }

    @ResponseBody
    @GetMapping("/token/{accessToken}")
    public ResponseEntity<String> places(@PathVariable String accessToken, Model model) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", accessToken);

        HttpEntity request = new HttpEntity(headers);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8090/places", HttpMethod.GET, request, String.class);
        return response;
    }

    @GetMapping("/{placeId}")
    public String placeDetail(@TokenUserId Long userId, @PathVariable Long placeId, Model model) {
        Company company = companyService.findById(userId);

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
    public String addPlace(@TokenUserId Long userId, @ModelAttribute PlaceAddDto placeAddDto, AddressDto addressDto, RedirectAttributes redirectAttributes) throws IOException {

        Place place = placeService.save(placeAddDto, addressDto, userId);
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
        return "redirect://signin";
    }
}