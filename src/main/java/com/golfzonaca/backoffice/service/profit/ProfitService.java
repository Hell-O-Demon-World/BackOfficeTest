package com.golfzonaca.backoffice.service.profit;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Reservation;
import com.golfzonaca.backoffice.repository.company.CompanyRepository;
import com.golfzonaca.backoffice.repository.reservation.ReservationRepository;
import com.golfzonaca.backoffice.service.profit.dto.ProfitDto;
import com.golfzonaca.backoffice.web.controller.profit.dto.ProfitSearchCond;
import com.golfzonaca.backoffice.web.controller.typeconverter.TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfitService {
    private final CompanyRepository companyRepository;
    private final ReservationRepository reservationRepository;

    public Map<Integer, ProfitDto> calculateProfit(Long companyId, ProfitSearchCond period) {
        Map<Integer, ProfitDto> profit = new LinkedHashMap<>();
        List<Place> placeList = companyRepository.findById(companyId).getPlaceList();
        for (int i = 0; i < placeList.size(); i++) {
            Place place = placeList.get(i);
            ProfitDto profitDto = new ProfitDto(place.getId(), place.getPlaceName(), 0L, 0L);
            for (Reservation reservation : reservationRepository.findByPlaceIdAndPeriod(place.getId(), TimeFormatter.toLocalDate(period.getStartDate()), TimeFormatter.toLocalDate(period.getEndDate()))) {
                profitDto.setReservationQuantity(profitDto.getReservationQuantity() + 1);
                profitDto.setProfit(profitDto.getProfit() + (long) (reservation.getResEndTime().getHour() - reservation.getResStartTime().getHour()) * reservation.getRoom().getRoomKind().getPrice());
            }
            profit.put(i, profitDto);
        }
        return profit;
    }
}
