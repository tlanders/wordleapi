package biz.lci.wordleapi.service;

import biz.lci.wordleapi.domain.WordleRecommendation;
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
        WordleRecommendation response = wordleService.recommend(List.of(
                "s-l-a?t!e-",
                "a?b?e-t!s-"
        ));

        assertNotNull(response);
        assertTrue(response.recommendedSolution().length() > 0);
        assertTrue(response.possibleSolutions().size() > 0);
        assertTrue(response.possibleSolutions().contains("batty"));
        assertFalse(response.possibleSolutions().contains("slate"));
/*
        response = wordleService.recommend(List.of(
                new WordleTurn("least", "--a?--"),
                new WordleTurn("adorn", "a?-o?--"),
                new WordleTurn("whack", "--a?-k?")
//                new WordleTurn("kazoo", "k!a!z!o!o!")
        ));

        assertNotNull(response);
        assertTrue(response.recommendedSolution().length() > 0);
        assertTrue(response.possibleSolutions().size() > 0);
        assertTrue(response.possibleSolutions().contains("kazoo"));
        assertFalse(response.possibleSolutions().contains("whack"));

 */
    }
}