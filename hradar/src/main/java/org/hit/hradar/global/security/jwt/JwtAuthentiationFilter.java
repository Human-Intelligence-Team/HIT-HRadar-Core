package org.hit.hradar.global.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.auth.AuthErrorCode;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.security.CustomUserDetails;
import org.hit.hradar.global.security.CustomUserDetailsService;
import org.hit.hradar.global.util.AuthRedisService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthentiationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailsService userDetailsService;
  private final AuthRedisService authRedisService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // 요청 헤더에서 JWT 토큰 추출
    String token = getJwtFromRequest(request);
    // 토큰이 있을 경우
    if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

      // 유저 정보 추출
      String loginId = jwtTokenProvider.getLoginIdFromJWT(token);
      // DB에서 사용자 정보 조회
      CustomUserDetails userDetails = userDetailsService.loadUserByUsername(loginId);

      // 인증 객체 생성
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      SecurityContextHolder.getContext()
          .setAuthentication(authenticationToken);
    }

    filterChain.doFilter(request, response);
  }

  /**
   * 헤더에서 JWT 토큰 추출
   *
   * @param request
   * @return
   */
  private String getJwtFromRequest(HttpServletRequest request) {
    log.debug("[JwtAuthentiationFilter/getJwtFromRequest] 헤더에서 JWT 토큰 추출");
    String bearerToken = request.getHeader("Authorization");

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7)
          .trim();

    }
    return null;
  }
}
