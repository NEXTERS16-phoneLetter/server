package com.nexters.phoneletter.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;

  @Override // ignore check swagger resource
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
        "/swagger-ui.html", "/webjars/**", "/swagger/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable() // REST API 임으로 기본설정 사용 x , 기본설정은 로그인 폼으로 이동함
        .csrf().disable() // REST API csrf disabled
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt token 은 session 필요 x
        .and()
          .authorizeRequests()
            .antMatchers("/*/signin", "/*/signup").permitAll() // 로그인, 회원 가입은 누구나 가능, 항상 허용
            .antMatchers(HttpMethod.GET, "/hello").permitAll() // test code hello world 도 허용
            .anyRequest().hasRole("USER")
        .and()
          .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),UsernamePasswordAuthenticationFilter.class);
          //Spring Security 많은 Filter 들은 순서가 있느데,요청권한이 없으면 기본 로그인폼으로 보내는 UsernamePasswordAuthenticationFilter 전에 jwt filter를 등록 해야함
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
