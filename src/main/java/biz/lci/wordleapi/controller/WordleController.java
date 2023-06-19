package biz.lci.wordleapi.controller;

import biz.lci.wordleapi.domain.WordleRecommendation;
import biz.lci.wordleapi.domain.WordleTurn;
import biz.lci.wordleapi.service.WordleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequestMapping("/api/wordle")
@RestController
public class WordleController {
    @Autowired
    private WordleService wordleService;

    @GetMapping("/hello")
    public Mono<String> hello() {
        log.debug("GET hello");
        return Mono.just("Hello Wordle");
    }

    @PostMapping("/recommend")
    public Mono<WordleRecommendation> recommendSolution(@RequestBody List<String> wordleResponses) {
        log.debug("POST recommend, wordleResponses={}", wordleResponses);
        return Mono.just(wordleService.recommend(wordleResponses));
    }
}
