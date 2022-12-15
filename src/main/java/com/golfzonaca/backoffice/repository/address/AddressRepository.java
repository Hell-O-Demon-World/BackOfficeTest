package com.golfzonaca.backoffice.repository.address;

import com.golfzonaca.backoffice.domain.Address;
import com.golfzonaca.backoffice.repository.address.dto.AddressUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class AddressRepository {

    private final JpaAddressRepository jpaRepository;
    private final QueryAddressRepository queryRepository;


    public Address save(Address address) {
        return jpaRepository.save(address);
    }

    public Address findById(long id) {
        return jpaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("등록되지 않은 주소입니다."));
    }

    public void update(Address address, AddressUpdateDto data) {
        address.updateAddress(data.getPostalCode(), data.getAddress(), 0D, 0D);
    }

    public void delete(Address address) {
        jpaRepository.delete(address);
    }
}
