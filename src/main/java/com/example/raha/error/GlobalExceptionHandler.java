package com.example.raha.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * エラーハンドリングを実装するクラス
 *
 * @author T.ARAKI
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 共通エラー処理
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "エラーが発生しました");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 409エラー処理
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> conflictException(ConflictException e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // ログイン認証失敗エラー処理
    @ExceptionHandler(invalidAuthenticationException.class)
    public ResponseEntity<Map<String, String>> authenticationException(invalidAuthenticationException e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
