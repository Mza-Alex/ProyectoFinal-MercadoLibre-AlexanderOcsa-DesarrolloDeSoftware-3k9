package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository repository;

    public long countMutants() {
        return repository.countByMutant(true);
    }

    public long countHumans() {
        return repository.countByMutant(false);
    }

    public double getRatio() {
        long humans = countHumans();
        long mutants = countMutants();

        if (humans == 0) {
            return mutants > 0 ? 1.0 : 0.0;
        }

        return (double) mutants / humans;
    }
}
