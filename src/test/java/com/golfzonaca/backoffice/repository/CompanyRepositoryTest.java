package com.golfzonaca.backoffice.repository;

import com.golfzonaca.backoffice.domain.Company;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class CompanyRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Test
    void 업체저장() {
        //Given
        Company company = new Company("testacc1", "test1234", "테스트 업체1", "02-1234-1234", "12-12341234", "어나니머스", 1L);
        //When
        Company savedCompany = companyRepository.save(company);
        //Then
        assertThat(savedCompany.getCompanyName()).isEqualTo(company.getCompanyName());
    }

    @Test
    void 모든업체조회() {
        //Given
        Company companyA = new Company("testacc1", "test1234", "테스트 업체1", "02-1234-1234", "12-12341234", "어나니머스", 1L);
        Company savedCompanyA = companyRepository.save(companyA);
        Company companyB = new Company("testacc2", "test1234", "테스트 업체2", "02-1234-4321", "12-12344321", "이너", 1L);
        Company savedCompanyB = companyRepository.save(companyB);
        //When
        List<Company> result = companyRepository.findAll();
        //Then
        assertThat(result).contains(savedCompanyA, savedCompanyB);
    }

    @Test
    void 업체아이디로조회() {
        //Given
        Company companyA = new Company("testacc1", "test1234", "테스트 업체1", "02-1234-1234", "12-12341234", "어나니머스", 1L);
        Company savedCompanyA = companyRepository.save(companyA);
        //When
        Optional<Company> findCompany = companyRepository.findByCompanyLoginId("testacc1");
        //Then
        assertThat(findCompany.get()).isEqualTo(savedCompanyA);
    }
}