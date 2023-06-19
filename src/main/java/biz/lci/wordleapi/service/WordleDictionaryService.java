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
        List<String> possibleWords = words.parallelStream().filter(buildPredicate(wordleResponses))
                .toList();

//        String turnRegex = buildRegex(wordleResponses.get(0));

//        List<String> possibleWords = words.parallelStream().filter(aWord -> {
//            return aWord.matches(turnRegex);
//        })
//                .toList();

        return possibleWords;
    }

    protected Predicate<String> buildPredicate(List<String> wordleResponses) {
        Predicate<String> predicate = buildPredicate(wordleResponses.get(0));
        for(int i = 1; i < wordleResponses.size(); i++) {
            predicate = predicate.and(buildPredicate(wordleResponses.get(i)));
        }
        return predicate;
    }

    protected Predicate<String> buildPredicate(String wordleResponse) {
        Predicate<String> predicate = null;

        for(int i = 0; i < 10; i += 2) {
            String currentLetter = wordleResponse.substring(i, i + 1);
            String letterStatus = wordleResponse.substring(i + 1, i + 2);

            Predicate<String> p = getPredicate(i / 2, currentLetter, letterStatus, wordleResponse);

            if(predicate == null) {
                predicate = p;
            } else {
                predicate = predicate.and(p);
            }
        }
        return predicate;
    }

    protected Predicate<String> getPredicate(int index, String letter, String status, String fullResponse) {
        Predicate<String> predicate = switch (status) {
            case "-" -> getCharNotFoundPredicate(letter, fullResponse);
            case "?" -> getCharInWordPredicate(index, letter, fullResponse);
            case "!" -> str -> str.charAt(index) == letter.charAt(0);
            default -> throw new RuntimeException("unknown status: " + status);
        };
        return predicate;
    }

    /**
     * Get a predicate that returns true if the word contains the letter, but not at the given index.
     */
    private static Predicate<String> getCharInWordPredicate(int index, String letter, String fullResponse) {
        // regex example: /^.*a(\?|!).*a(\?|!).*$/
        String charAppearsMultipleTimesInWordRegex = "^.*" + letter + "(\\?|!).*" + letter + "(\\?|!).*$";
//        log.debug("charAppearsMultipleTimesInWordRegex={}", charAppearsMultipleTimesInWordRegex);
        if(fullResponse.matches(charAppearsMultipleTimesInWordRegex)) {
            // regex example: /^[^a]*a[^a]*a[^a]*$/
            final String charAppearsTwiceInWordRegex = "^[^" + letter + "]*" + letter + "[^" + letter + "]*" + letter + "[^" + letter + "]*$";
            log.debug("charAppearsTwiceInWordRegex={}", charAppearsTwiceInWordRegex);
            return str -> str.matches(charAppearsTwiceInWordRegex);
        } else {
            return str -> str.contains(letter) && str.charAt(index) != letter.charAt(0);
        }
    }

    private static Predicate<String> getCharNotFoundPredicate(String letter, String fullResponse) {
        // regex example: /^.*a(\?|!).*$/
        String charAppearsMultipleTimesInWordRegex = "^.*" + letter + "(\\?|!).*$";
//        log.debug("charAppearsMultipleTimesInWordRegex={}", charAppearsMultipleTimesInWordRegex);
        if(fullResponse.matches(charAppearsMultipleTimesInWordRegex)) {
            String charOnlyInWordOnceRegex = "^[^" + letter + "]*" + letter + "[^" + letter + "]*$";
            log.debug("charOnlyInWordOnceRegex={}", charOnlyInWordOnceRegex);
            // regex example: /[^a]*a[^a]*/
            return str -> str.matches(charOnlyInWordOnceRegex);
        } else {
            return str -> !str.contains(letter);
        }
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
