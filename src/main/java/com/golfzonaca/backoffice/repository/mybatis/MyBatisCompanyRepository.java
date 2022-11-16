package com.golfzonaca.backoffice.repository.mybatis;

import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MyBatisCompanyRepository implements CompanyRepository {
    private final CompanyMapper companyMapper;

    @Override
    public Company save(Company company) {
        companyMapper.save(company);
        return company;
    }

    @Override
    public List<Company> findAll() {
        return companyMapper.findAll();
    }

    @Override
    public Optional<Company> findByCompanyLoginId(String companyLoginId) {
        return companyMapper.findByCompanyLoginId(companyLoginId);
    }

    @Override
    public Company findById(Long id) {
        return companyMapper.findById(id);
    }
}