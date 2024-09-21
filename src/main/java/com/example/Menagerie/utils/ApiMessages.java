package com.example.Menagerie.utils;

public enum ApiMessages {

    DATA_FETCH(200, "Data Fetched Successfully"),
    DATA_ADDED(200, "Data Added Successfully"),
    DATA_UPDATED(200, "Data Updated Successfully"),
    EVENT_ADDED(200, "Event Added Successfully"),
    PET_DELETED(200, "Pet Deleted Successfully"),
    VALIDATION_FAILED(400, "Validation Failed, Please provide a valid data"),
    ERROR_OCCURRED(400, "An unexpected error occurred");

    private final int code;
    private final String message;

    ApiMessages(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
