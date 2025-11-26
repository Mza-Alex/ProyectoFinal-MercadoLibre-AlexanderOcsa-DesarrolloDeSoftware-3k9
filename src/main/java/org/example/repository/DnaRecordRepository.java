package org.example.repository;

import org.example.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    Optional<DnaRecord> findByHash(String hash);
    long countByMutant(boolean mutant);
}
