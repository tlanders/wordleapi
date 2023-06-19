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
        assertTrue(response.possibleSolutions().contains("batty"), "batty should be a solution");
        assertFalse(response.possibleSolutions().contains("slate"), "slate isn't a valid solution");
        assertFalse(response.possibleSolutions().contains("party"), "party isn't a valid solution");

        // solution is kazoo
        response = wordleService.recommend(List.of(
                "l-e-a?s-t-",
                "a?d-o?r-n-",
                "w-h-a?c-k?",
                "k!a!b-o!b-"
        ));

        assertNotNull(response);
        assertTrue(response.recommendedSolution().length() > 0);
        assertTrue(response.possibleSolutions().size() > 0);
        assertTrue(response.possibleSolutions().contains("kazoo"), "kazoo should be a possible solution");
        assertFalse(response.possibleSolutions().contains("whack"), "whack isn't a valid solution");
        assertEquals("kazoo", response.recommendedSolution(), "kazoo is the exact solution");

        // solution is kayak
        response = wordleService.recommend(List.of(
                "s-l-a?t-e-",
                "p-a!r-k?a?",
                "b-r-i-c-k!"
        ));

        assertNotNull(response);
        assertTrue(response.recommendedSolution().length() > 0);
        assertTrue(response.possibleSolutions().size() > 0);
        assertFalse(response.possibleSolutions().contains("kazoo"), "kazoo isn't a valid solution");
        assertFalse(response.possibleSolutions().contains("whack"), "whack isn't a valid solution");
        assertEquals("kayak", response.recommendedSolution(), "kayak is the exact solution");
    }
}