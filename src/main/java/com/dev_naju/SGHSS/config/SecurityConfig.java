package com.dev_naju.SGHSS.config;

import com.dev_naju.SGHSS.enums.ProfessionalRole;
import com.dev_naju.SGHSS.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/consultations/schedule").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/consultations/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/consultations/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/consultations").hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_PROFESSIONAL_HEALTH"
                        )
                        .requestMatchers(HttpMethod.GET, "/api/professionals/me").authenticated()
                        .requestMatchers("/api/professionals/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/professionals").hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_PROFESSIONAL_HEALTH"
                        )
                        .requestMatchers("/api/units/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/admin/**").hasAuthority(UserRole.ADMIN.name())

                        .requestMatchers("/api/patients/").hasAnyRole(
                                UserRole.PATIENT.name(),
                                UserRole.PROFESSIONAL_HEALTH.name(),
                                UserRole.ADMIN.name()
                        )
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}