package com.example.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityAuthentication {

    @Value("${spring.security.user.name}")
    private String userName;
    @Value("${spring.security.user.password}")
    private String password;

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails userOne = users
                                .username(userName)
                                .password(password)
                                .roles("USER").build();

        return new InMemoryUserDetailsManager(userOne);
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/v1/user/**")
                .permitAll().anyRequest().authenticated()// .antMatchers("/**")
                .and().formLogin().permitAll().and().logout().permitAll()
                .and().httpBasic();

        http.cors().disable().csrf().disable();

        /*http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/external/**")
                .permitAll().anyRequest().authenticated();*/
        return http.build();
    }
    /*public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                (req) -> req
                        .antMatchers("/api/v1/user/**")
                        .permitAll()
                        .anyRequest().authenticated()
        ).formLogin();

        return http.build();
    }*/

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/api/v1/user/**",
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }*/
}
