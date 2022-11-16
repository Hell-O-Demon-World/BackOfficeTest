package com.golfzonaca.backoffice.auth.handler;

import com.golfzonaca.backoffice.auth.token.JwtManager;
import com.golfzonaca.backoffice.auth.token.JwtRepository;
import com.golfzonaca.backoffice.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtRepository jwtRepository;
    private final CompanyRepository companyRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Jwt jwt = JwtManager.createJwt(companyRepository.findByCompanyLoginId((String) authentication.getPrincipal()).get().getId().toString());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        if (iter.hasNext()) {
            response.setStatus(HttpStatus.ACCEPTED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.addHeader("Authorization",jwt.getEncoded());
            request.setAttribute("Authorization", jwt.getEncoded());
            jwtRepository.save((String) authentication.getPrincipal(), jwt.getEncoded());
            response.sendRedirect("/places");
        }
    }

}
