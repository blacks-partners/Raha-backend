package com.example.raha.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizeFilter extends OncePerRequestFilter {
    private final RequestMatcher matcher;

    public AuthorizeFilter() {
        // this.matcher = new OrRequestMatcher(
        //         Arrays.asList(
        //                 new AntPathRequestMatcher("/login"),
        //                 new AntPathRequestMatcher("/register"),
        //                 new AntPathRequestMatcher("/check-email"),
        //                 new AntPathRequestMatcher("/articles/*", HttpMethod.GET.toString()),
        //                 new AntPathRequestMatcher("/articles", HttpMethod.GET.toString())));
        this.matcher = new AntPathRequestMatcher("/");
    }

    @Autowired
    @Value("${jwt.secret}")
    private String secret;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (matcher.matches(request)) {
            // リクエストヘッダーから "X-AUTH-TOKEN" を取得
            String xAuthToken = request.getHeader("X-AUTH-TOKEN");

            // トークンが存在しないか、"Bearer " で始まらない場合はエラーメッセージをレスポンス
            if (xAuthToken == null || !xAuthToken.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\":\"ログインしてください。\"}");
                return;
            }

            try {
                // トークンの "Bearer " 部分を取り除き、実際のトークンを取得
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build()
                        .verify(xAuthToken.substring(7));

                // デコードされたJWTから "userId" クレームを取得し、ユーザー名として使用
                Integer userId = Integer.parseInt(decodedJWT.getClaim("userId").toString());

                // 認証情報を作成し、セキュリティコンテキストに設定
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>()));
            } catch (TokenExpiredException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\":\"セッションが切れました。再度ログインしてください。\"}");
                return;
            } catch (JWTVerificationException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\":\"認証に失敗しました。再度ログインしてください。\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
