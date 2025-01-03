package com.gymapp.model;

import java.util.Arrays;

public enum MembershipType {
    ONE_MONTH("1-Month", "30 days"),
    THREE_MOTHNS("3-Months", "90 days"),
    ONE_YEAR("1-Year", "1 year");

    private String value;
    private String dateModifier;

    MembershipType(String value, String dateModifier) {
        this.value = value;
        this.dateModifier = dateModifier;
    }

    public String toString() {
        return this.value;
    }

    public String toDateModifier() {
        return this.dateModifier;
    }

    /**
     * @return The Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static MembershipType fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(MembershipType.values())
                .filter(e -> e.value.equals(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown value: " + s));
    }


}
