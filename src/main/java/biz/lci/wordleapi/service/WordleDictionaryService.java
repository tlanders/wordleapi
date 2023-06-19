package biz.lci.wordleapi.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
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
//        Predicate<String> p1 = str -> (str.charAt(0) == 'a');
//        return p1.and(str -> str.charAt(1) == 'b');
        Predicate<String> predicate = null;

        for(int i = 0; i < 10; i += 2) {
            String currentLetter = wordleResponse.substring(i, i + 1);
            String letterStatus = wordleResponse.substring(i + 1, i + 2);

            Predicate<String> p = getPredicate(i / 2, currentLetter, letterStatus);

            if(predicate == null) {
                predicate = p;
            } else {
                predicate = predicate.and(p);
            }
        }
        return predicate;
    }

    protected Predicate<String> getPredicate(int index, String letter, String status) {
        Predicate<String> predicate = null;
        switch(status) {
            case "-":
                predicate = str -> !str.contains(letter);
                break;
            case "?":
                predicate = str -> str.contains(letter);
                break;
            case "!":
                predicate = str -> str.charAt(index) == letter.charAt(0);
                break;
            default:
                throw new RuntimeException("unknown status: " + status);
        }
        return predicate;
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
