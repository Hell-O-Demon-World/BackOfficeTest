package com.golfzonaca.backoffice.repository.company;

import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.domain.CompanyAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCompanyTokenRepository extends JpaRepository<CompanyAccessToken, Long> {
    Optional<CompanyAccessToken> findFirstByCompanyIs(Company company);

    CompanyAccessToken save(CompanyAccessToken companyAccessToken);
}
