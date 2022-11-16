package com.golfzonaca.backoffice.auth.provider;

import com.golfzonaca.backoffice.auth.token.IdPwAuthenticationToken;
import com.golfzonaca.backoffice.auth.token.JwtManager;
import com.golfzonaca.backoffice.auth.token.JwtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.JwtHelper;

@Slf4j
@RequiredArgsConstructor
public class IdPwAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final GrantedAuthoritiesMapper grantedAuthoritiesMapper;
    private final JwtRepository jwtRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        jwtRepository.clearAll();
        String userId = String.valueOf(authentication.getPrincipal());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        if (!this.passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials");
        }

        IdPwAuthenticationToken certifiedToken = new IdPwAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(), grantedAuthoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
        certifiedToken.setDetails(authentication.getDetails());

        return certifiedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (IdPwAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
