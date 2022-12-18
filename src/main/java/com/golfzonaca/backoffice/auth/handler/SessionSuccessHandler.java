package com.golfzonaca.backoffice.auth.handler;

import com.golfzonaca.backoffice.auth.token.JwtManager;
import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.domain.CompanyAccessToken;
import com.golfzonaca.backoffice.repository.company.CompanyRepository;
import com.golfzonaca.backoffice.repository.company.CompanyTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class SessionSuccessHandler implements AuthenticationSuccessHandler {
    private final CompanyRepository companyRepository;
    private final CompanyTokenRepository companyTokenRepository;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Company findCompany = companyRepository.findByLoginId(authentication.getPrincipal().toString());
        Jwt accessJwt = JwtManager.createAccessJwt(findCompany.getId());
        String jwtEncoded = accessJwt.getEncoded();
        if (companyTokenRepository.isExistByCompany(findCompany)) {
            CompanyAccessToken findAccessToken = companyTokenRepository.findAccessTokenByCompany(findCompany);
            findAccessToken.setAccessToken(jwtEncoded);
        } else {
            CompanyAccessToken companyAccessToken = CompanyAccessToken.builder()
                    .accessToken(jwtEncoded)
                    .company(findCompany)
                    .build();
            companyTokenRepository.save(companyAccessToken);
        }
        redirectStrategy.sendRedirect(request, response, "/places");
    }
}
