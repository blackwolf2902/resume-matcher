package com.resume.jsb.util;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ResumeScorer {

    private static final Map<String, Double> SKILL_LIBRARY = Map.ofEntries(
        Map.entry("python", 1.0),
        Map.entry("java", 1.2),
        Map.entry("spring", 1.3),
        Map.entry("spring boot", 1.5),
        Map.entry("django", 1.2),
        Map.entry("flask", 1.1),
        Map.entry("react", 1.2),
        Map.entry("angular", 1.2),
        Map.entry("node", 1.1),
        Map.entry("express", 1.0),
        Map.entry("mysql", 1.0),
        Map.entry("postgresql", 1.0),
        Map.entry("mongodb", 1.1),
        Map.entry("docker", 1.3),
        Map.entry("kubernetes", 1.4),
        Map.entry("aws", 1.4),
        Map.entry("azure", 1.3),
        Map.entry("git", 0.8),
        Map.entry("github", 0.8),
        Map.entry("rest api", 1.2),
        Map.entry("microservices", 1.4),
        Map.entry("machine learning", 1.5),
        Map.entry("data analysis", 1.3),
        Map.entry("html", 0.9),
        Map.entry("css", 0.9),
        Map.entry("javascript", 1.0)
    );

    public Map<String, Object> calculateScore(String jdText, String resumeText) {
        jdText = jdText.toLowerCase();
        resumeText = resumeText.toLowerCase();

        Set<String> jdSkills = extractSkills(jdText);
        Set<String> resumeSkills = extractSkills(resumeText);

        Set<String> matchedSkills = new HashSet<>(jdSkills);
        matchedSkills.retainAll(resumeSkills);

        double jdTotalWeight = jdSkills.stream()
                .mapToDouble(skill -> SKILL_LIBRARY.getOrDefault(skill, 1.0))
                .sum();

        double matchedWeight = matchedSkills.stream()
                .mapToDouble(skill -> SKILL_LIBRARY.getOrDefault(skill, 1.0))
                .sum();

        double score = jdTotalWeight == 0 ? 0 : (matchedWeight / jdTotalWeight) * 100.0;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("score", Math.round(score * 100.0) / 100.0);
        result.put("jdSkills", jdSkills);
        result.put("resumeSkills", resumeSkills);
        result.put("matchedSkills", matchedSkills);

        return result;
    }

    private Set<String> extractSkills(String text) {
        Set<String> foundSkills = new HashSet<>();

        for (String skill : SKILL_LIBRARY.keySet()) {
            String regex = "\\b" + Pattern.quote(skill) + "\\b";
            if (Pattern.compile(regex).matcher(text).find()) {
                foundSkills.add(skill);
            }
        }
        return foundSkills;
    }
}
