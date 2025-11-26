package org.example.controller;

import org.example.dto.StatsResponse;
import org.example.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("stats")
    public ResponseEntity<StatsResponse> getStats() {

        long mutants = statsService.countMutants();
        long humans = statsService.countHumans();
        double ratio = statsService.getRatio();

        StatsResponse response = new StatsResponse(mutants, humans, ratio);

        return ResponseEntity.ok(response);
    }
}
