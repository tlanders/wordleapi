package biz.lci.wordleapi.service;

import biz.lci.wordleapi.domain.WordleTurn;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class WordleDictionaryService {
    @Value("classpath:wordle-dictionary.txt")
    private org.springframework.core.io.Resource wordleDictionaryFile;

    protected List<String> words;

    @PostConstruct
    protected void loadDictionary() throws IOException {
        words = Files.readAllLines(wordleDictionaryFile.getFile().toPath());
        log.debug("loaded {} words", words.size());
    }

    public List<String> findMatches(List<String> wordleResponses) {
//        {"guess":"slate", "wordleResponse":"--a?t!-"},
//        {"guess":"abets", "wordleResponse":"a?b?-t!-"}

//        String turnRegex = buildRegex(wordleResponses.get(0));

        List<String> possibleWords = words.parallelStream().filter(buildPredicate(wordleResponses.get(0)))
                .toList();

//        List<String> possibleWords = words.parallelStream().filter(aWord -> {
//            return aWord.matches(turnRegex);
//        })
//                .toList();
        return possibleWords;
    }

    protected Predicate<String> buildPredicate(String wordleResponse) {
        Predicate<String> p1 = str -> (str.charAt(0) == 'a');
        return p1.and(str -> str.charAt(1) == 'b');
    }

    /**
     * match 5-letter word with only one 'a'.
     * ^(?=.{5}$)[^aA]*[aA][^aA]*$
     *
     * c must be in 2nd position. only one 'a' and one 'b' in rest of word.
     * ^(?=.{5}$)[^abc][cC]([^ac]*[aA][^bc]*[bB][^ac]*|[^bc]*[bB][^ac]*[aA][^bc]*)$
     *
     * @param wordleResponse
     * @return
     */
    protected String buildRegex(String wordleResponse) {
        // simple, hacky version
        String regex = "^(?=.{5}$)";    // positive look
        int spotsChecked = 0;
        int index = 0;
        while(spotsChecked++ < 5) {
            char currentLetter = wordleResponse.charAt(index);
            if(currentLetter == '-') {
                regex += '.';
                index++;
            } else {

            }
        }
        return regex;
    }
}
