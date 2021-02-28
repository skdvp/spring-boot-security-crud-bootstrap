package com.skdvp.app.security;

import com.skdvp.app.security.handler.SuccessUserHandler;
import com.skdvp.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userDetailsService; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    public SecurityConfig(@Lazy UserServiceImpl userDetailsService, SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }


    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.logout()

                // разрешаем делать логаут всем
                .permitAll()

                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login")

                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable();


        http.authorizeRequests()

                .antMatchers("/").permitAll() // доступность всем

                .antMatchers("/login").anonymous() //страницы аутентификаци доступна всем

                .antMatchers("/user").access("hasAnyRole('ROLE_ADMIN','ROLE_USER')") // разрешаем входить на /user пользователям с ролью User

                .antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN')").anyRequest().authenticated() // защищенные URL

                .and().formLogin()  // Spring сам подставит свою логин форму

                .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправления по ролям
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
