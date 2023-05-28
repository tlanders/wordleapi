package biz.lci.wordleapi.controller;

import biz.lci.wordleapi.domain.WordleTurn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/wordle")
@RestController
public class WordleController {
    @GetMapping("/hello")
    public Mono<String> hello() {
        log.debug("GET hello");
        return Mono.just("Hello Wordle");
    }

    @PostMapping("/recommend")
    public Mono<Map> recommendSolution(@RequestBody List<WordleTurn> turns) {
        return Mono.just(Map.of("recommendedSolution", "ABCDE"));
    }
}
