package com.golfzonaca.backoffice.repository.payment;

import com.golfzonaca.backoffice.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value = "select payment.ID, payment.RESERVATION_ID, payment.PAY_DATE,payment.PAY_TIME, payment.PAY_PRICE, payment.PAY_MILEAGE, payment.PAY_WAY, payment.SAVED_MILEAGE, payment.PAY_TYPE, payment.PAY_API_CODE, payment.PG, payment.PAY_STATUS from payment join reservation res on res.ID = payment.RESERVATION_ID join room on res.ROOM_ID = room.ID join place p on room.PLACE_ID = p.ID WHERE p.id = :placeId and payment.PAY_STATUS NOT LIKE 'CANCELED'", nativeQuery = true)
    List<Payment> findPaymentForDeskAndMeetingRoomByPlace(@Param("placeId") Long placeId);

    @Query(value = "select payment.ID, payment.RESERVATION_ID, payment.PAY_DATE,payment.PAY_TIME, payment.PAY_PRICE, payment.PAY_MILEAGE, payment.PAY_WAY, payment.SAVED_MILEAGE, payment.PAY_TYPE, payment.PAY_API_CODE, payment.PG, payment.PAY_STATUS from payment join reservation res on res.ID = payment.RESERVATION_ID join room on res.ROOM_ID = room.ID join place p on room.PLACE_ID = p.ID WHERE p.id = :placeId and payment.PAY_DATE >= :startDate and payment.PAY_STATUS NOT LIKE 'CANCELED'", nativeQuery = true)
    List<Payment> findPaymentForDeskAndMeetingRoomByPlaceAndStartDate(@Param("placeId") Long placeId, @Param("startDate") LocalDate startDate);

    @Query(value = "select payment.ID, payment.RESERVATION_ID, payment.PAY_DATE,payment.PAY_TIME, payment.PAY_PRICE, payment.PAY_MILEAGE, payment.PAY_WAY, payment.SAVED_MILEAGE, payment.PAY_TYPE, payment.PAY_API_CODE, payment.PG, payment.PAY_STATUS from payment join reservation res on res.ID = payment.RESERVATION_ID join room on res.ROOM_ID = room.ID join place p on room.PLACE_ID = p.ID WHERE p.id = :placeId and payment.PAY_DATE <= :endDate and payment.PAY_STATUS NOT LIKE 'CANCELED'", nativeQuery = true)
    List<Payment> findPaymentForDeskAndMeetingRoomByPlaceAndEndDate(@Param("placeId") Long placeId, @Param("endDate") LocalDate endDate);

    @Query(value = "select payment.ID, payment.RESERVATION_ID, payment.PAY_DATE,payment.PAY_TIME, payment.PAY_PRICE, payment.PAY_MILEAGE, payment.PAY_WAY, payment.SAVED_MILEAGE, payment.PAY_TYPE, payment.PAY_API_CODE, payment.PG, payment.PAY_STATUS from payment join reservation res on res.ID = payment.RESERVATION_ID join room on res.ROOM_ID = room.ID join place p on room.PLACE_ID = p.ID WHERE p.id = :placeId and payment.PAY_DATE >= :startDate and payment.PAY_DATE <= :endDate and payment.PAY_STATUS NOT LIKE 'CANCELED'", nativeQuery = true)
    List<Payment> findPaymentForDeskAndMeetingRoomByPlaceAndPeriod(@Param("placeId") Long placeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
