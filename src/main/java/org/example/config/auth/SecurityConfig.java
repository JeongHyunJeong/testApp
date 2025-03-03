package org.example.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerOAuth2UserService customerOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //
        http.csrf().disable().headers().frameOptions().disable()
        //
        .and().authorizeRequests()
        //
        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
        //
        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
        //
        .anyRequest().authenticated()
        //
        .and().logout().logoutSuccessUrl("/")
        //
        .and().oauth2Login()
        //
        .userInfoEndpoint().userService(customerOAuth2UserService);
    }

}
