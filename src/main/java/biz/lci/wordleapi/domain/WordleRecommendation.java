package biz.lci.wordleapi.domain;

import java.util.List;

public record WordleRecommendation(String recommendedSolution, List<String> possibleSolutions) {
}
