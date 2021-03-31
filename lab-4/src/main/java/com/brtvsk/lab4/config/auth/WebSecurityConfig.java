package com.brtvsk.lab4.config.auth;

import com.brtvsk.lab4.model.auth.type.Permission;
import com.brtvsk.lab4.repository.auth.UserRepository;
import com.brtvsk.lab4.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin", "/admin/**").hasAuthority(Permission.VIEW_ADMIN.name())
                .antMatchers("/catalog").hasAuthority(Permission.VIEW_CATALOG.name())
                .antMatchers("/profile").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .csrf().disable()
                .logout().permitAll();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new MyUserDetailsService(userRepository);
    }
}
