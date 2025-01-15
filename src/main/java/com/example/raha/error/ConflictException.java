package com.example.raha.error;

/**
 * 409エラー用例外クラス
 *
 * @author T.Araki
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
