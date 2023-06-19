package biz.lci.wordleapi.service;

import biz.lci.wordleapi.domain.WordleResponse;
import biz.lci.wordleapi.domain.WordleTurn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WordleService {
    private final WordleDictionaryService dictionary;

    public WordleService(WordleDictionaryService dictionary) {
        this.dictionary = dictionary;
    }

    public WordleResponse recommend(List<WordleTurn> turns) {
        List<String> possibleSolutions = dictionary.findMatches(turns);
        return new WordleResponse(possibleSolutions.get(0), possibleSolutions);
    }
}
