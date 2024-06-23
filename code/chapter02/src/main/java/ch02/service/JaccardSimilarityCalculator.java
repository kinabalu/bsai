package ch02.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JaccardSimilarityCalculator {
    public double calculateJaccardSimilarity(String text1, String text2) {
        // Preprocess texts
        Set<String> set1 = new HashSet<>(preprocessText(text1));
        Set<String> set2 = new HashSet<>(preprocessText(text2));

        // Calculate intersection
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        // Calculate union
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        // Calculate Jaccard similarity
        return (double) intersection.size() / union.size();
    }

    private List<String> preprocessText(String text) {
        return Arrays.stream(text.toLowerCase().split("\\W+"))
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
    }
}
