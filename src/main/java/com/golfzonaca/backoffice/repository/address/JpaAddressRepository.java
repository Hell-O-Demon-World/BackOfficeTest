package com.golfzonaca.backoffice.repository.address;

import com.golfzonaca.backoffice.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface JpaAddressRepository extends JpaRepository<Address, Long> {
}
