package com.golfzonaca.backoffice.web.transformtype;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.repository.dto.PlaceUpdateDto;
import com.golfzonaca.backoffice.web.form.place.PlaceAddForm;
import com.golfzonaca.backoffice.web.form.place.PlaceEditForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TransformType {

    public Place listToString(PlaceAddForm placeAddForm) {
        Place place = new Place();
        place.setPlaceName(placeAddForm.getPlaceName());
        place.setCompanyId(placeAddForm.getCompanyId());
        place.setPlaceDescription(placeAddForm.getPlaceDescription());
        place.setPlaceOpenDays(placeAddForm.getPlaceOpenDays().toString().replace("[", "").replace("]", ""));
        place.setPlaceStart(placeAddForm.getPlaceStart());
        place.setPlaceEnd(placeAddForm.getPlaceEnd());
        place.setPlaceAddInfo(placeAddForm.getPlaceAddInfo().toString().replace("[", "").replace("]", ""));
        place.setAddressId(placeAddForm.getAddressId());
        return place;
    }

    public PlaceAddForm stringToList(Place place) {
        PlaceAddForm placeAddForm = new PlaceAddForm();
        placeAddForm.setId(place.getId());
        placeAddForm.setCompanyId(place.getCompanyId());
        placeAddForm.setPlaceName(place.getPlaceName());
        placeAddForm.setPlaceDescription(place.getPlaceDescription());
        placeAddForm.setPlaceOpenDays(new ArrayList<>(Arrays.asList(place.getPlaceOpenDays().split(", "))));
        placeAddForm.setPlaceStart(place.getPlaceStart());
        placeAddForm.setPlaceEnd(place.getPlaceEnd());
        placeAddForm.setPlaceAddInfo(List.of(place.getPlaceAddInfo().split(", ")));
        placeAddForm.setAddressId(place.getAddressId());
        return placeAddForm;
    }

    public PlaceUpdateDto editTransform(PlaceEditForm placeEditForm) {
        PlaceUpdateDto place = new PlaceUpdateDto();
        place.setPlaceName(placeEditForm.getPlaceName());
        place.setPlaceDescription(placeEditForm.getPlaceDescription());
        place.setPlaceOpenDays(placeEditForm.getPlaceOpenDays().toString().replace("[", "").replace("]", ""));
        place.setPlaceStart(placeEditForm.getPlaceStart());
        place.setPlaceEnd(placeEditForm.getPlaceEnd());
        place.setPlaceAddInfo(placeEditForm.getPlaceAddInfo().toString().replace("[", "").replace("]", ""));
        place.setAddressId(placeEditForm.getAddressId());
        return place;
    }
}
