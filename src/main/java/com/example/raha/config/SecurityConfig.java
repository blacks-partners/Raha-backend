package com.example.raha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.raha.service.LoginUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthorizeFilter authorizeFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF（クロスサイトリクエストフォージェリ）保護を無効化
        http.csrf(csrf -> csrf.disable());

        // CORS（クロスオリジンリソースシェアリング）の設定を適用
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/articles/*").permitAll()
                .anyRequest().authenticated());

        // カスタムの認可フィルタをセキュリティフィルタチェーンに追加
        http.addFilterBefore(authorizeFilter, UsernamePasswordAuthenticationFilter.class)
                // セッション管理をステートレスに設定（セッションを使用しない）
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ユーザー情報の取得とパスワードの照合を行う
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(LoginUserDetailsService userService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // ユーザー詳細サービスを設定（ユーザー情報の取得に使用）
        provider.setUserDetailsService(userService);
        // パスワードエンコーダーを設定（パスワードの照合に使用）
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // CORSの設定を定義
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.addExposedHeader("X-AUTH-TOKEN");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 全てのパスに対してCORS設定を適用
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
