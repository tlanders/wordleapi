package biz.lci.wordleapi.service;

import biz.lci.wordleapi.domain.WordleTurn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WordleDictionaryServiceTest {
    @Autowired
    private WordleDictionaryService dictionary;

    @Test
    public void findMatches() {
        List<String> matches = dictionary.findMatches(List.of(
                "--a?t!-",
                "a?b?-t!-"
        ));

        assertTrue(matches.size() > 0);
        assertTrue(matches.contains("abate"));
        assertFalse(matches.contains("slate"));
    }
}