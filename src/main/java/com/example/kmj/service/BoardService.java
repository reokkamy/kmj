package com.example.kmj.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class BoardService{
    public BoardService(Object o) {
    }

    public String filterBadWords(String text) {
    try {
        ClassPathResource resource = new ClassPathResource("static/badwords.txt");
        List<String> badWords = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);

        for (String word : badWords) {
            String masked = "*".repeat(word.length());
            text = text.replaceAll(Pattern.quote(word), masked);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return text;
}}
