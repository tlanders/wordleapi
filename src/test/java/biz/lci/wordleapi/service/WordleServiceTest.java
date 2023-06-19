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

        testExactSolution("kayak", List.of("s-l-a?t-e-", "p-a!r-k?a?", "b-r-i-c-k!"));
        testExactSolution("clasp", List.of("p?a?r-k-a-", "a?l!o-u-d-", "c!l!a!m-p!"));
        testExactSolution("kayak", List.of("p-a!r-k?a?", "w-a!c-k?y?"));
        testExactSolution("ozone", List.of("c-o?c-o?a-", "s-h-o!n!e!"));
    }

    private void testExactSolution(String exactSolution, List<String> wordleResponses) {
        WordleRecommendation response = wordleService.recommend(wordleResponses);

        assertNotNull(response);
        assertTrue(response.recommendedSolution().length() > 0, "There should be a recommended solution");
        assertTrue(response.possibleSolutions().size() == 1, "There should only be 1 possible solution");
        assertEquals(exactSolution, response.recommendedSolution(), exactSolution + " is the exact solution");
    }
}