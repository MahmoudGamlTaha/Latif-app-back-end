package com.commerce.backend.helper;

import java.util.Map;

import lombok.Data;

@Data
public class Note {
    private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
}