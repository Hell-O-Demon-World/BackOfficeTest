package com.golfzonaca.backoffice.auth.filter;

import com.golfzonaca.backoffice.auth.token.IdPwAuthenticationToken;
import com.golfzonaca.backoffice.auth.token.JwtManager;
import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.repository.company.CompanyRepository;
import com.golfzonaca.backoffice.repository.place.PlaceRepository;
import com.golfzonaca.backoffice.service.company.CompanyService;
import com.golfzonaca.backoffice.service.place.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final CompanyService companyService;
    private final PlaceService placeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        String path = request.getServletPath();

        if (jwt != null && JwtManager.validateJwt(jwt)) {
//            String[] pathParts = path.split("/");
//
            Long companyId = JwtManager.getIdByToken(jwt);
            String loginId = companyService.findById(companyId).getLoginId();
//            Long placeId = placeService.findByCompanyId(companyId).getId();
//            for (int i = 0; i < pathParts.length; i++) {
//                System.out.println("pathParts = " + pathParts[i]);
//
//            }
//            System.out.println("pathParts[2] = " + pathParts[2]);
//            System.out.println("findPlaceId = " + placeId);
//            if (Long.parseLong(pathParts[2]) == placeId) {
                Authentication authentication = getAuthentication(loginId);
                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        return new IdPwAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }
}
