package biz.lci.wordleapi.domain;

import java.util.Collections;
import java.util.List;

public class WordleDictionary {
    List<String> words = List.of("apple, brown", "green");

    public List<String> findMatches(String matchStr) {
        return Collections.unmodifiableList(words);
    }
}
