package com.golfzonaca.backoffice.repository.payment;

import com.golfzonaca.backoffice.domain.Payment;
import com.golfzonaca.backoffice.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PaymentRepository {
    private final JpaPaymentRepository jpaRepository;
    private final QueryPaymentRepository queryRepository;


    public List<Payment> findPaymentForDeskAndMeetingRoomByPlaceIdAndPeriod(Place place, LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return jpaRepository.findPaymentForDeskAndMeetingRoomByPlace(place.getId());
        } else if (startDate != null && endDate == null) {
            return jpaRepository.findPaymentForDeskAndMeetingRoomByPlaceAndStartDate(place.getId(), startDate);
        } else if (startDate == null) {
            return jpaRepository.findPaymentForDeskAndMeetingRoomByPlaceAndEndDate(place.getId(), endDate);
        } else {
            return jpaRepository.findPaymentForDeskAndMeetingRoomByPlaceAndPeriod(place.getId(), startDate, endDate);
        }
    }
}
