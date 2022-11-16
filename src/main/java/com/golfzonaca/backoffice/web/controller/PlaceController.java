package com.golfzonaca.backoffice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golfzonaca.backoffice.auth.token.JwtRepository;
import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.domain.Location;
import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.domain.type.AddInfoType;
import com.golfzonaca.backoffice.domain.type.DaysType;
import com.golfzonaca.backoffice.domain.type.RoomType;
import com.golfzonaca.backoffice.repository.CompanyRepository;
import com.golfzonaca.backoffice.repository.LocationRepository;
import com.golfzonaca.backoffice.repository.PlaceRepository;
import com.golfzonaca.backoffice.repository.RoomRepository;
import com.golfzonaca.backoffice.repository.dto.LocationUpdateDto;
import com.golfzonaca.backoffice.repository.dto.PlaceUpdateDto;
import com.golfzonaca.backoffice.web.form.place.PlaceAddForm;
import com.golfzonaca.backoffice.web.form.place.PlaceEditForm;
import com.golfzonaca.backoffice.web.form.room.RoomAddForm;
import com.golfzonaca.backoffice.web.transformtype.TransformType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/places") //기본 url 시작을 위한 RequestMapping
@RequiredArgsConstructor
public class PlaceController {
    private final JwtRepository jwtRepository;
    private final PlaceRepository placeRepository;
    private final RoomRepository roomRepository;
    private final CompanyRepository companyRepository;
    private final LocationRepository locationRepository;
    private final TransformType transformType;

    @ModelAttribute("DaysType")
    public DaysType[] daysType() {
        return DaysType.values();
    }

    @ModelAttribute("AddInfoType")
    public AddInfoType[] addInfoType() {
        return AddInfoType.values();
    }

    @ModelAttribute("RoomType")
    public RoomType[] roomType() {
        return RoomType.values();
    }

    @GetMapping
    public String places(Model model) throws JsonProcessingException {
        Company company = companyRepository.findById(jwtRepository.getUserId());
        List<Place> places = placeRepository.findAll(company.getId());
        model.addAttribute("places", places);
        model.addAttribute("companyLoginId", jwtRepository.getId());
        return "place/places";
    }

    @GetMapping("/{placeId}")
    public String place(@PathVariable Long placeId, Model model) {
        Place place = placeRepository.findById(placeId).get();
        Location location = locationRepository.findByAddressId(place.getAddressId());

        List<Integer> roomTypeQuantity = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            roomTypeQuantity.add(0);
        }

        for (int i = 0; i < roomRepository.countByRoomType(placeId).size(); i++) {
            List<Integer> result = new ArrayList<>(roomRepository.countByRoomType(placeId).get(i).values());
            roomTypeQuantity.set((new Integer(String.valueOf(result.get(0))) - 1), new Integer(String.valueOf(result.get(1))));
        }

        PlaceAddForm placeAddForm = transformType.stringToList(place);
        model.addAttribute("place", placeAddForm);
        model.addAttribute("location", location);
        model.addAttribute("rooms", roomTypeQuantity);
        return "place/place";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("place", new Place());
        model.addAttribute("roomQuantity", new RoomAddForm());
        model.addAttribute("location", new Location());
        return "place/addForm";
    }

    @PostMapping("/add")
    public String addPlace(@ModelAttribute PlaceAddForm placeAddForm, Location location, RoomAddForm roomAddForm, RedirectAttributes redirectAttributes) throws JsonProcessingException {

        Location savedAddress = locationRepository.save(location);
        placeAddForm.setAddressId(savedAddress.getId());

        placeAddForm.setCompanyId(jwtRepository.getUserId());

        Place place = transformType.listToString(placeAddForm);
        Place savedPlace = placeRepository.save(place);

        for (int i = 0; i < roomAddForm.getDesk(); i++) {
            roomRepository.save(new Room(1L, savedPlace.getId(), savedPlace.getCompanyId(), 1, false));
        }
        for (int i = 0; i < roomAddForm.getMeetingRoom4(); i++) {
            roomRepository.save(new Room(2L, savedPlace.getId(), savedPlace.getCompanyId(), 4, false));
        }
        for (int i = 0; i < roomAddForm.getMeetingRoom6(); i++) {
            roomRepository.save(new Room(3L, savedPlace.getId(), savedPlace.getCompanyId(), 6, false));
        }
        for (int i = 0; i < roomAddForm.getMeetingRoom10(); i++) {
            roomRepository.save(new Room(4L, savedPlace.getId(), savedPlace.getCompanyId(), 10, false));
        }
        for (int i = 0; i < roomAddForm.getMeetingRoom20(); i++) {
            roomRepository.save(new Room(5L, savedPlace.getId(), savedPlace.getCompanyId(), 20, false));
        }
        for (int i = 0; i < roomAddForm.getOffice20(); i++) {
            roomRepository.save(new Room(6L, savedPlace.getId(), savedPlace.getCompanyId(), 20, false));
        }
        for (int i = 0; i < roomAddForm.getOffice40(); i++) {
            roomRepository.save(new Room(7L, savedPlace.getId(), savedPlace.getCompanyId(), 40, false));
        }
        for (int i = 0; i < roomAddForm.getOffice70(); i++) {
            roomRepository.save(new Room(8L, savedPlace.getId(), savedPlace.getCompanyId(), 70, false));
        }
        for (int i = 0; i < roomAddForm.getOffice100(); i++) {
            roomRepository.save(new Room(9L, savedPlace.getId(), savedPlace.getCompanyId(), 100, false));
        }

        redirectAttributes.addAttribute("id", savedPlace.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/places/{id}";
    }

    @GetMapping("/{placeId}/edit")
    public String editForm(@PathVariable Long placeId, Model model) {
        Place findPlace = placeRepository.findById(placeId).get();
        PlaceAddForm place = transformType.stringToList(findPlace);
        Location location = locationRepository.findByAddressId(place.getAddressId());

        List<Integer> roomTypeQuantity = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            roomTypeQuantity.add(0);
        }

        for (int i = 0; i < roomRepository.countByRoomType(placeId).size(); i++) {
            List<Integer> result = new ArrayList<>(roomRepository.countByRoomType(placeId).get(i).values());
            roomTypeQuantity.set((new Integer(String.valueOf(result.get(0))) - 1), new Integer(String.valueOf(result.get(1))));
        }

        model.addAttribute("place", place);
        model.addAttribute("location", location);
        model.addAttribute("rooms", roomTypeQuantity);
        return "place/editForm";
    }

    @PostMapping("/{placeId}/edit")
    public String edit(@PathVariable Long placeId, @ModelAttribute PlaceEditForm updateViewParam, LocationUpdateDto updateParam) {
        long addressId = placeRepository.findById(placeId).get().getAddressId();
        updateViewParam.setAddressId(addressId);
        PlaceUpdateDto place = transformType.editTransform(updateViewParam);
        placeRepository.update(placeId, place);
        locationRepository.update(addressId, updateParam);
        return "redirect:/places/{placeId}";
    }

    @GetMapping("/{placeId}/delete")
    public String delete(@PathVariable Long placeId) {
        locationRepository.delete(placeRepository.findById(placeId).get().getAddressId());
        placeRepository.delete(placeId);
        roomRepository.deleteByPlaceId(placeId);
        return "redirect:/places";
    }

    @GetMapping("/signout")
    public String signout() {
        jwtRepository.clearAll();
        return "redirect:/signin";
    }
}