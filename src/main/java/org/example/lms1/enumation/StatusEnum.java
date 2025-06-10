package org.example.lms1.enumation;

import lombok.Getter;

@Getter
public enum StatusEnum {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

}