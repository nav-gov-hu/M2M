package com.example.demo.util;

import java.time.LocalDate;

public class LocalDateAdapter {

    public static LocalDate parse(String value) {
        return value != null ? LocalDate.parse(value) : null;
    }
    public static String print(LocalDate value) {
        return value != null ? value.toString() : null;
    }
}
