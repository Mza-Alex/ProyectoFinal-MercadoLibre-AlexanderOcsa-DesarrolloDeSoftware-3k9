package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dna_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String hash;

    private boolean mutant;

    // Para MutantService
    public DnaRecord(String hash, boolean mutant) {
        this.hash = hash;
        this.mutant = mutant;
    }
}
