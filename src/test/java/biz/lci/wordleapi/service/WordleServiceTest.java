package biz.lci.wordleapi.service;

import biz.lci.wordleapi.domain.WordleRecommendation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

        assertThat(response).isNotNull();

        assertThat(response.recommendedSolution()).hasSizeGreaterThan(0);

        assertThat(response.possibleSolutions())
                .hasSizeGreaterThan(0)
                .contains("batty")
                .doesNotContain("slate", "party");

        // solution is kazoo
        response = wordleService.recommend(List.of(
                "l-e-a?s-t-",
                "a?d-o?r-n-",
                "w-h-a?c-k?",
                "k!a!b-o!b-"
        ));

        assertThat(response).isNotNull();

        assertThat(response.recommendedSolution())
                .hasSizeGreaterThan(0)
                .isEqualTo("kazoo");

        assertThat(response.possibleSolutions())
                .hasSizeGreaterThan(0)
                .contains("kazoo")
                .doesNotContain("whack");

        testPossibleSolutions(List.of("yummy", "nymph", "dummy"), List.of("m?a-y?b-e-"));
        testPossibleSolutions(List.of("gamma", "comma"), List.of("m-u-m!m!y-"));
        testPossibleSolutions(List.of("frost"), List.of("s?l-a-t?e-", "g-h-o!s!t!"));   // 6/20 wordle (wordlebot suggested "trout" instead of ghost)
        testPossibleSolutions(List.of("quest", "guest", "chest"), List.of("s?t?a-i-n-", "b-l-e!s!t!"));   // 6/26 wordle

        testExactSolution("kayak", List.of("k!a!y!a!k!"));
        testExactSolution("kayak", List.of("s-l-a?t-e-", "p-a!r-k?a?", "b-r-i-c-k!"));
        testExactSolution("clasp", List.of("p?a?r-k-a-", "a?l!o-u-d-", "c!l!a!m-p!"));
        testExactSolution("kayak", List.of("p-a!r-k?a?", "w-a!c-k?y?"));
        testExactSolution("ozone", List.of("c-o?c-o?a-", "s-h-o!n!e!"));
        testExactSolution("onion", List.of("c-a-n?o!n!", "u-n!i!o!n!")); // "n?y-l-o!n!"
        testExactSolution("yummy", List.of("m?a-y?b-e-", "c-o-y?l-y!", "g-u!p-p-y!")); // "n?y-l-o!n!"
        testExactSolution("eerie", List.of("s-n-e?e?r?", "g-e!n-r?e!", "s-e!r!v-e!"));
        testExactSolution("mummy", List.of("a-m?m!o-s-", "m!i-m!i-c-"));
        testExactSolution("gamma", List.of("m-u-m!m!y-", "b-e-a?c-h-"));
        testExactSolution("guest", List.of("s?t?a-i-n-", "b-l-e!s!t!", "c-h-e!s!t!", "q-u!e!s!t!"));   // 6/26 wordle
    }

    private void testExactSolution(String exactSolution, List<String> wordleResponses) {
        WordleRecommendation response = wordleService.recommend(wordleResponses);

        assertThat(response).isNotNull();

        assertThat(response.recommendedSolution()).hasSizeGreaterThan(0);

        assertThat(response.possibleSolutions())
                .hasSize(1)
                .containsExactly(exactSolution);
    }

    private void testPossibleSolutions(List<String> expectedPossibleSolutions, List<String> wordleResponses) {
        WordleRecommendation response = wordleService.recommend(wordleResponses);

        assertThat(response).isNotNull();

        assertThat(response.recommendedSolution()).hasSizeGreaterThan(0);

        assertThat(response.possibleSolutions())
                .hasSizeGreaterThan(0)
                .containsAll(expectedPossibleSolutions);
    }
}