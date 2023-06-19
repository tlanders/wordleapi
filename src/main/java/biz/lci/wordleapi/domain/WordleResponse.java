package biz.lci.wordleapi.domain;

import java.util.List;

public record WordleResponse(String recommendedSolution, List<String> possibleSolutions) {
}
