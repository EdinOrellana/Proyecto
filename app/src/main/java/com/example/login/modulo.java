package com.example.login;

import java.util.regex.Pattern;

public class modulo {
    public boolean ValidateEmail(String email){
        String regex="^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,3})$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
    public boolean ValidateContrsena(String contra){
        String regex="^[a-z0-9_-]{6,18}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(contra).matches();
    }
}
