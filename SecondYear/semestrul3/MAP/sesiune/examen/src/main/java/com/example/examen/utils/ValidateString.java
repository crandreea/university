package com.example.examen.utils;

public class ValidateString {
    public static boolean validateString(String str) {
        return str != null && str.trim().length() > 2;
    }
}
