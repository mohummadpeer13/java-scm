package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    // @Bean
    // public UserDetailsService userDetailsService(){
    // UserDetails user = User
    // .withDefaultPasswordEncoder()
    // .username("admin123")
    // .password("admin123")
    // .roles("ADMIN","USER")
    // .build();

    // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
    // return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenticationSuccessHandler OAuth2handler;
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // urls configure
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form login default
        // httpSecurity.formLogin(Customizer.withDefaults());
        
        // form login customize
        httpSecurity.formLogin(formLogin ->{
            formLogin.loginPage("/login");
            formLogin.defaultSuccessUrl("/user/dashboard");
            formLogin.failureUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // oauth2 configurations
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(OAuth2handler);
        });

        httpSecurity.logout(logoutForm -> {
            logoutForm.deleteCookies("JSESSIONID");
            logoutForm.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
            logoutForm.logoutSuccessUrl("/");
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
