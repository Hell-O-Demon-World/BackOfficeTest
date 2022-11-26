package com.golfzonaca.backoffice.repository.company;

import com.golfzonaca.backoffice.domain.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class CompanyRepository {
    private final JpaCompanyRepository jpaRepository;
    private final QueryCompanyRepository queryRepository;

    public Optional<Company> findById(Long id) {
        return jpaRepository.findById(id);
    }

    public Company findByLoginId(String loginId) {
        return jpaRepository.findByLoginId(loginId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 계정입니다."));
//        return queryRepository.findByLoginId(loginId);
    }
}