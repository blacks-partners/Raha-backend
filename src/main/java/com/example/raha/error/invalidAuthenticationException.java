package com.example.raha.error;

import org.springframework.security.core.AuthenticationException;

/**
 * ログイン認証エラー用例外クラス
 * @author hosodatomoya
 */
public class invalidAuthenticationException extends AuthenticationException {

    public invalidAuthenticationException(String message) {
        super(message);
    }
    
}
