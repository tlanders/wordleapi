package biz.lci.wordleapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WordleDictionaryServiceTest {
    @Autowired
    private WordleDictionaryService dictionary;

    @Test
    public void findMatches() {
//        {"guess":"slate", "wordleResponse":"--a?t!-"},
//        {"guess":"abets", "wordleResponse":"a?b?-t!-"}
    }
}