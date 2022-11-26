package com.golfzonaca.backoffice.service.company;

import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;

    public Company findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회사입니다."));
    }

    public Company findByLoginId(String loginId) {
        return repository.findByLoginId(loginId);
    }
}
