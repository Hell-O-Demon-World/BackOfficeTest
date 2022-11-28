package com.golfzonaca.backoffice.service.company;

import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;

    public Company findById(Long id) {
        return repository.findById(id);
    }

    public Company findByLoginId(String loginId) {
        return repository.findByLoginId(loginId);
    }
}
