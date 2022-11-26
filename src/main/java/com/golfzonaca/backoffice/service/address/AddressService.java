package com.golfzonaca.backoffice.service.address;

import com.golfzonaca.backoffice.domain.Address;
import com.golfzonaca.backoffice.repository.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public void delete(Address address) {
        addressRepository.delete(address);
    }
}
