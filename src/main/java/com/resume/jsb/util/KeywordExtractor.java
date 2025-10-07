package com.resume.jsb.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

public class KeywordExtractor {

    public static List<String> extractKeywords(String text, int topN) throws Exception {
        Map<String, Integer> freqMap = new HashMap<>();

        Analyzer analyzer = new EnglishAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(text));
        tokenStream.reset();

        while (tokenStream.incrementToken()) {
            String token = tokenStream.getAttribute(CharTermAttribute.class).toString();
            if (token.length() > 2 && !token.matches("\\d+")) {
                freqMap.put(token, freqMap.getOrDefault(token, 0) + 1);
            }
        }
        tokenStream.end();
        tokenStream.close();
        analyzer.close();

        // Sort by frequency descending and pick top N
        return freqMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
