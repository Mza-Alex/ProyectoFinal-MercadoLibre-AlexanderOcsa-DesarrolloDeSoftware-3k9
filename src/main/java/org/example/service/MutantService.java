package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class MutantService {

    private final DnaRecordRepository repository;

    public MutantService(DnaRecordRepository repository) {
        this.repository = repository;
    }


    public boolean isMutant(String[] dna) {

        //Generar hash único del ADN
        String hash = hashDna(dna);

        //Buscar si ya existe en BD
        Optional<DnaRecord> existing = repository.findByHash(hash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        //Detectar si es mutante
        boolean isMutant = detectMutant(dna);

        //Guardar el resultado
        repository.save(new DnaRecord(hash, isMutant));

        return isMutant;
    }



    private static final int SEQ = 4;

    private boolean detectMutant(String[] dna) {
        int n = dna.length;
        char[][] m = new char[n][n];

        for (int i = 0; i < n; i++) {
            m[i] = dna[i].toCharArray();
        }

        int count = 0;

        int[][] directions = {
                {0, 1},
                {1, 0},
                {1, 1},
                {1, -1}
        };

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                for (int[] d : directions) {

                    int dx = d[0], dy = d[1];
                    char c = m[i][j];

                    int x = i, y = j;
                    int k = 0;

                    while (k < SEQ &&
                            x >= 0 && x < n &&
                            y >= 0 && y < n &&
                            m[x][y] == c) {

                        x += dx;
                        y += dy;
                        k++;
                    }

                    if (k == SEQ) {
                        count++;

                        if (count > 1) return true;
                    }
                }
            }
        }
        return false;
    }

    //Aca se genera un hash SHA-256 único por cada adn, evitando procesar 2 veces el mismo adn
    private String hashDna(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String joined = String.join("", dna);
            byte[] encoded = digest.digest(joined.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : encoded) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }
    public String generateHash(String[] dna) {
        return String.join("-", dna);  //mismo hash para mismo adn
    }
}
