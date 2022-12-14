package com.golfzonaca.backoffice.repository.company;

import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.domain.CompanyAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
@RequiredArgsConstructor
public class CompanyTokenRepository {
    private final JpaCompanyTokenRepository jpaCompanyTokenRepository;

    public CompanyAccessToken findAccessTokenByCompany(Company company) {
        return jpaCompanyTokenRepository.findFirstByCompanyIs(company).orElseThrow(() -> new RuntimeException("Access 토큰이 존재하지 않습니다."));
    }

    public Boolean isExistByCompany(Company company) {
        return jpaCompanyTokenRepository.findFirstByCompanyIs(company).isPresent();
    }

    public CompanyAccessToken save(CompanyAccessToken companyAccessToken) {
        return jpaCompanyTokenRepository.save(companyAccessToken);
    }

}
