package biz.lci.wordleapi.service;

import biz.lci.wordleapi.domain.WordleRecommendation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        log.debug("responses={}, possibleSolutions={}", wordleResponses, possibleSolutions);
        return new WordleRecommendation(possibleSolutions.get(0), possibleSolutions);
    }
}
