package com.hu.myblog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/plugins/**").permitAll()
                .antMatchers("/admin/**").access("@authService.auth(request, authentication)")
                .antMatchers("/pages/**").authenticated()
                .and().formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/admin/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/pages/main.html")
                .failureUrl("/login.html")
                .permitAll()
                .and().logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/login.html")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin();


    }
}
