package com.example.proj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestMatcherRegistry ->
                        requestMatcherRegistry
                                .requestMatchers("/css/**").permitAll()
                                .anyRequest().authenticated())
                // Custom Login  Page Configuration
//                .formLogin(form -> form
//                        .loginPage("/login").permitAll()
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/home")
//                        .failureUrl("/login?error=true")
//                )
//                .logout(form -> form
//                        .invalidateHttpSession(true)  //If you Logout Invalidate User Session
//                        .clearAuthentication(true)  //Authentication is leaded
//                        .logoutRequestMatcher(request -> "/logout".equals(request.getRequestURI()))
//                        .logoutSuccessUrl("/login?logout=true")
//                        .permitAll()
//                )
                .formLogin(form ->
                        form.loginPage("/login").permitAll()
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/home")
                                .failureUrl("/login?error=true"))
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(request -> "/logout".equals(request.getRequestURI()))
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())
                .oauth2Login(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/home")
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("aaa")
                .password(passwordEncoder().encode("abc"))
                .authorities("USER")
                .build();
        return new InMemoryUserDetailsManager(user);

    }


}
