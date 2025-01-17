package com.example.raha.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserForm {
    private String name;
    private String email;
    private String introduction;
}
