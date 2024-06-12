package com.dci.a3m.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public AuthenticationSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from users where username=?");

        userDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");

        return userDetailsManager;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        String[] staticResources = {
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/scripts/**",
        };

        String[] publicPages = {
                "/login-form",
                "/mvc/member-form",
                "/mvc/member-form/create",
                "login-success"
        };

        String[] restAPI = {
                "/api/**",

        };


        http
                .authorizeHttpRequests(config -> config
                        .requestMatchers(publicPages).permitAll()
                        .requestMatchers(staticResources).permitAll()
                        .requestMatchers(restAPI).permitAll()
                        .requestMatchers("/admin-dashboard/**").hasRole( "ADMIN")
                        .requestMatchers("/mvc/**").hasRole("MEMBER")
                        .anyRequest().authenticated())


//                        .requestMatchers("/restricted/**").hasRole("ADMIN"))
//                      .requestMatchers("/mvc/**").hasAnyRole("MEMBER", "ADMIN")
//                .requestMatchers("/restricted/**").hasRole("ADMIN"))
//                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login-form")
                        .loginProcessingUrl("/authenticate")
                        .successHandler(customLoginSuccessHandler())
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login-form")
                        .permitAll());

        return http.build();
    }

}
