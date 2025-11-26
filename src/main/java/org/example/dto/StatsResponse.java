package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatsResponse {
    private long count_mutant_dna;
    private long count_human_dna;
    private double ratio;
}
