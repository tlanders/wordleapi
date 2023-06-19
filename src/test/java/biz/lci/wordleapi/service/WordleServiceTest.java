package biz.lci.wordleapi.service;

import biz.lci.wordleapi.domain.WordleResponse;
import biz.lci.wordleapi.domain.WordleTurn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WordleServiceTest {
    @Autowired
    private WordleService wordleService;

    @Test
    public void testRecommend() {
        WordleResponse response = wordleService.recommend(List.of(
                new WordleTurn("slate", "--a?t!-"),
                new WordleTurn("abets", "a?b?-t!-")
        ));

        assertNotNull(response);
        assertTrue(response.recommendedSolution().length() > 0);
        assertTrue(response.possibleSolutions().size() > 0);
        assertTrue(response.possibleSolutions().contains("abate"));
        assertFalse(response.possibleSolutions().contains("slate"));
    }
}