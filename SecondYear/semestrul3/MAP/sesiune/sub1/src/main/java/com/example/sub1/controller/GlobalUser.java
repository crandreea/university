package com.example.sub1.controller;

import com.example.sub1.domain.Persoana;

public class GlobalUser {

    private static Persoana persoana = null;

    public static Persoana getPersoana() {
        return persoana;
    }

    public static void setPersoana(Persoana persoana) {
        GlobalUser.persoana = persoana;
    }
}
