package biz.lci.wordleapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WordleDictionaryServiceTest {
    @Autowired
    private WordleDictionaryService dictionary;

    @Test
    public void findMatches() {
        List<String> matches = dictionary.findMatches(List.of(
                "s-l-a?t!e-",
                "a?b?e-t!s-"
        ));

        assertThat(matches)
                .as("Should be at least 1 match")
                .hasSizeGreaterThan(0)
                .as("batty should be a possible solution")
                .contains("batty")
                .as("slate or party are not possible solutions")
                .doesNotContain("slate", "party");
    }
}