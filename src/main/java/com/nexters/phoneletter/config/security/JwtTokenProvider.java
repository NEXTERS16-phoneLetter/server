package com.nexters.phoneletter.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider { //jwt 생성 및 검증 모듈

  static final String JWT_HEADER = "X-AUTH-TOKEN";

  @Value("${spring.jwt.secret}")
  private String secretKey; // jwt secret key

  @Value("${spring.jwt.errorKey}")
  private String REQUEST_JWT_ATT_KEY;

  private static final long TOKEN_VALID_MILISECOND = 1000L * 60 * 60; //1시간 후 만료

  private final UserDetailsService userDetailsService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  //jwt 생성 메서드
  public String createToken(String userId, List<String> roles) {
    Claims claims = Jwts.claims().setSubject(userId);
    claims.put("roles", roles);
    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims) //jwt에 들어갈 데이터
        .setIssuedAt(now) // 토큰 발행 일자
        .setExpiration(new Date(now.getTime() + TOKEN_VALID_MILISECOND)) //만료 시간
        .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
        .compact();
  }

  //jwt 토큰으로 인증정보 조회
  @Transactional
  public Authentication getAuthentication(String token) {
    //TODO: DB -> Redis 변경 ?
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // jwt 에서 회원 정보 추출
  public String getUserId(String token) {

    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  // Request의 Header에서 token 파싱
  public String resolveToken(HttpServletRequest req) {
    return req.getHeader(JWT_HEADER);
  }

  // Jwt 토큰의 유효성 + 만료일자 확인
  public boolean validateToken(String jwtToken, ServletRequest request, ServletResponse response)
      throws IOException {

    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (SignatureException ex) {
      request.setAttribute(REQUEST_JWT_ATT_KEY, "Invalid JWT Signature");
    } catch (MalformedJwtException ex) {
      request.setAttribute(REQUEST_JWT_ATT_KEY, "Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      request.setAttribute(REQUEST_JWT_ATT_KEY, "Expired JWT token");
//      request.setAttribute("expired",ex.getMessage());
    } catch (UnsupportedJwtException ex) {
      request.setAttribute(REQUEST_JWT_ATT_KEY, "Unsupported JWT exception");
    } catch (IllegalArgumentException ex) {
      request.setAttribute(REQUEST_JWT_ATT_KEY, "Jwt claims string is empty");
    }
    return false;
  }
}
