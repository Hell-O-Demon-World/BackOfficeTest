package com.golfzonaca.backoffice.config;

import com.golfzonaca.backoffice.auth.filter.JsonIdPwAuthenticationProcessingFilter;
import com.golfzonaca.backoffice.auth.filter.SessionAuthenticationFilter;
import com.golfzonaca.backoffice.auth.provider.IdPwAuthenticationProvider;
import com.golfzonaca.backoffice.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SessionAuthenticationFilter sessionAuthenticationFilter;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthService authService;

    private static final RequestMatcher LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher("/signin", "POST");
    @Bean
    public PasswordEncoder passwordEncoder() {  // passwordEncoder라는 인터페이스를 BcryptPasswordEncoder가 implement 하기 떄문에 new 가능!
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JsonIdPwAuthenticationProcessingFilter jsonIdPwAuthenticationProcessingFilter() throws Exception {
        JsonIdPwAuthenticationProcessingFilter jsonAuthenticationFilter = new JsonIdPwAuthenticationProcessingFilter(LOGIN_REQUEST_MATCHER);
        jsonAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        return jsonAuthenticationFilter;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(2);
        http.formLogin()
                .loginPage("/signin")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/places/token/**", "/places/add/**").permitAll()
                .antMatchers("/**").hasRole("REGISTER")
                .and()
                .logout(logout -> logout
                        .logoutUrl("/signout")
                        .logoutSuccessUrl("/signin")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));
        http.addFilterAt(jsonIdPwAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(sessionAuthenticationFilter, JsonIdPwAuthenticationProcessingFilter.class);
        http.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new IdPwAuthenticationProvider(authService, passwordEncoder()));
    }

}
