package com.example.Menagerie.utils;

public enum Sex {
    M("m"),
    F("f");

    private final String value;

    Sex(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Sex fromValue(String value) {
        for (Sex sex : values()) {
            if (sex.value.equalsIgnoreCase(value)) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Invalid value for Sex: " + value);
    }
}

