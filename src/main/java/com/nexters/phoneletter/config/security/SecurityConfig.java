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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
        .csrf().disable() // CSRF : 사용자가 자신의 행위와 무관하게 다른 공격자가 의도한 행위를 하게 만드는 것(post patch delete)
        // Spring Security는 default 활성화. HttpSession과 동일한 생명주기를 가지는 Token 발행(GET).->요청마다 Token에 발행 되어있는지 확인으로 CSRF 예방
        // header에 csrf token 정보를 포함해서 전송 하면 됨 개발 편의성을 위해서 비활성화.
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt token 은 session 필요 x

        .and()
        .authorizeRequests()
        .antMatchers("/*/signin", "/*/signup").permitAll() // 로그인, 회원 가입은 누구나 가능, 항상 허용
        .antMatchers("/sms", "/sms/verify").permitAll() // 전화번호 인증
        .antMatchers(HttpMethod.GET, "/hello").permitAll() // test code hello world 도 허용
        .anyRequest().hasRole("USER")

        .and()
        .addFilterAt(new JwtAuthenticationFilter(jwtTokenProvider), BasicAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
