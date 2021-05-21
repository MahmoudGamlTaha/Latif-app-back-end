package com.commerce.backend.constants;

import lombok.Getter;

@Getter
public enum ReportType {
    UNSEEN(value.UNSEEN),
    REPORT(value.REPORT),
    INTEREST(value.INTEREST);

    private String type;

    ReportType(String type) {
        this.type = type;
    }

    public static class value{
        public static final String UNSEEN = "UNSEEN";
        public static final String REPORT = "REPORT";
        public static final String INTEREST = "INTEREST";
    }
}
