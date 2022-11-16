package com.golfzonaca.backoffice.config;

import com.golfzonaca.backoffice.auth.filter.JsonIdPwAuthenticationProcessingFilter;
import com.golfzonaca.backoffice.auth.filter.JwtAuthenticationFilter;
import com.golfzonaca.backoffice.auth.handler.JwtSuccessHandler;
import com.golfzonaca.backoffice.auth.provider.IdPwAuthenticationProvider;
import com.golfzonaca.backoffice.auth.token.JwtRepository;
import com.golfzonaca.backoffice.repository.mybatis.CompanyMapper;
import com.golfzonaca.backoffice.repository.mybatis.MyBatisCompanyRepository;
import com.golfzonaca.backoffice.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CompanyMapper companyMapper;
    private static final RequestMatcher LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher("/signin", "POST");
    @Bean
    public PasswordEncoder passwordEncoder() {  // passwordEncoder라는 인터페이스를 BcryptPasswordEncoder가 implement 하기 떄문에 new 가능!
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthService(new MyBatisCompanyRepository(companyMapper));
    }
    @Bean
    public JwtRepository jwtRepostiory() {
        return new JwtRepository();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userDetailsService());
    }
    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        return new NullAuthoritiesMapper();
    }
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new JwtSuccessHandler(jwtRepostiory(),new MyBatisCompanyRepository(companyMapper));
    }
    @Bean
    public JsonIdPwAuthenticationProcessingFilter jsonIdPwAuthenticationProcessingFilter() throws Exception {
        JsonIdPwAuthenticationProcessingFilter jsonAuthenticationFilter = new JsonIdPwAuthenticationProcessingFilter(LOGIN_REQUEST_MATCHER);
        jsonAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        return jsonAuthenticationFilter;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin()
                .loginPage("/signin").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/").hasAnyRole();
        http.addFilterAt(jsonIdPwAuthenticationProcessingFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), JsonIdPwAuthenticationProcessingFilter.class);
        http.userDetailsService(userDetailsService());
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new IdPwAuthenticationProvider(userDetailsService(),
                passwordEncoder(),
                new SimpleAuthorityMapper(),
                jwtRepostiory()));
    }

}
