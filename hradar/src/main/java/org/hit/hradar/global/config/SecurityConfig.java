package org.hit.hradar.global.config;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.global.security.CustomUserDetailsService;
import org.hit.hradar.global.security.RestAccessDeniedHandler;
import org.hit.hradar.global.security.RestAuthenticationEntryPoint;
import org.hit.hradar.global.security.jwt.JwtAuthentiationFilter;
import org.hit.hradar.global.security.jwt.JwtTokenProvider;
import org.hit.hradar.global.util.AuthRedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;                    // 토큰 생성/검증
  private final CustomUserDetailsService userDetailsService;          // 사용자 정보 로드
  private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  private final RestAccessDeniedHandler restAccessDeniedHandler;
  private final AuthRedisService authRedisService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http

        // CORS 설정 활성화
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        // CSRF 비활성화 (JWT 사용)
        .csrf(AbstractHttpConfigurer::disable)

        // 세션을 사용하지 않는 Stateless 설정
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        // URL / Method 별 인가 규칙
        .authorizeHttpRequests(auth -> {

          /* Swagger 문서 공개 */
          auth.requestMatchers(
                  "/api/v1/**",
                  "/v3/api-docs/**",
                  "/swagger-ui/**",
                  "/swagger-ui.html"
              )
              .permitAll();

          /* 정적 리소스 & SSE 테스트 페이지 */
          auth.requestMatchers(
                  "/",              // 루트
                  "/index.html",
                  "/sse-test.html", // SSE 테스트용 HTML
                  "/static/**",
                  "/css/**",
                  "/js/**",
                  "/images/**",
                  "/favicon.ico",
                  "/uploads/**"
              )
              .permitAll();

          /* 위에서 명시하지 않은 모든 요청은 인증 필요 */
          auth.anyRequest()
              .authenticated();
        })

        // JWT 인증 필터 추가
        .addFilterBefore(jwtAuthentiationFilter(), UsernamePasswordAuthenticationFilter.class)

        // 인증 / 인가 실패 처리
        .exceptionHandling(exception ->
            exception
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler)
        );

    return http.build();
  }

  @Bean
  public JwtAuthentiationFilter jwtAuthentiationFilter() {
    return new JwtAuthentiationFilter(jwtTokenProvider, userDetailsService, authRedisService);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowedOrigins(List.of("http://localhost:5173"));
    config.setAllowedOriginPatterns(List.of("https://*.trycloudflare.com"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    config.setAllowCredentials(true);
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
