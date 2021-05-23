package com.andy.springbootjpa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // api protection and authorities
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/post/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/api/v1/user/login")
                .usernameParameter("email").passwordParameter("password")
                .successHandler(new AuthSuccessHandler())
                .failureHandler(new AuthFailureHandler())
                .and().exceptionHandling()
                .authenticationEntryPoint(new AuthEntryPoint())
                .and().logout().logoutUrl("/api/v1/user/logout").deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new OnLogoutSuccessHandler())
                .and().csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
