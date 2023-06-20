package biz.lci.wordleapi.service;

import biz.lci.wordleapi.domain.WordleRecommendation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class WordleService {
    private final WordleDictionaryService dictionary;

    public WordleService(WordleDictionaryService dictionary) {
        this.dictionary = dictionary;
    }

    public WordleRecommendation recommend(List<String> wordleResponses) {
        List<String> possibleSolutions = dictionary.findMatches(wordleResponses);
        if(possibleSolutions.isEmpty()) {
            // TODO - handle this case better
            return new WordleRecommendation("", List.of());
        } else {
            List<WordScore> scoredSolutions = scoreSolutions(possibleSolutions);
            log.debug("responses={}, possibleSolutions={}", wordleResponses, possibleSolutions);
            return new WordleRecommendation(
                    scoredSolutions.get(0).word(),
                    scoredSolutions.stream().map(WordScore::word).toList());
        }
    }

    private static List<WordScore> scoreSolutions(List<String> possibleSolutions) {
        // first heuristic, score each work based on how many unique commonly used letters it contains. pick the one with the highest score.
        return possibleSolutions.parallelStream()
                .map(aWord -> {
                    int score = 0;
                    List<String> checkedLetters = new ArrayList<>();
                    for(int i = 0; i < aWord.length(); i++) {
                        String letter = aWord.substring(i, i + 1);
                        if(!checkedLetters.contains(letter)) {
                            // only count a letter once
                            checkedLetters.add(letter);
                            score += getLetterScore(letter);
                        } else {
                            // penalize words that have duplicate letters
                            score -= 3;
                        }
                    }
                    return new WordScore(aWord, score > 0 ? score : 0);
                })
                .sorted(Comparator.comparing(WordScore::score).reversed())
                .toList();
    }

    private static int getLetterScore(String letter) {
        return switch (letter) {
            case "a", "e", "i", "o", "u" -> 1;
            case "r" -> 7;
            case "t", "n" -> 6;
            case "s", "l" -> 5;
            case "c" -> 4;
            case "d", "p", "m", "h" -> 3;
            case "g", "b" -> 2;
            case "f", "y", "w", "k", "v" -> 1;
            default -> 0;
        };
    }

    private record WordScore(String word, int score) implements Comparable<WordScore> {
        @Override
        public int compareTo(WordScore wordScore2) {
            return this.score - wordScore2.score;
        }
    }
}
