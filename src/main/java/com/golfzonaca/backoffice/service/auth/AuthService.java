package com.golfzonaca.backoffice.service.auth;

import com.golfzonaca.backoffice.auth.CompanyPrincipalDetails;
import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final CompanyRepository companyRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Company company = companyRepository.findByLoginId(username);
        Set<GrantedAuthority> grantedAuthorityList = new HashSet<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_REGISTER"));

        return CompanyPrincipalDetails.builder()
                .username(company.getLoginId())
                .password(company.getPw())
                .authorities(grantedAuthorityList)
                .build();
    }
}
