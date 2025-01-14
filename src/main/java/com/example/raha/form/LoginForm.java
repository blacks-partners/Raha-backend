package com.example.raha.form;

import lombok.Data;

/**
 * メールアドレスとパスワードを受け取る用のクラス
 * @author hosodatomoya
 */
@Data
public class LoginForm {
    private String email;
    private String password;
}
