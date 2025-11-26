package org.example.validator;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class DnaValidator {

    public static void validate(String[] dna) {

        //adn null
        if (dna == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "DNA no puede ser null"
            );
        }

        //vacío
        if (dna.length == 0) {
            throw new IllegalArgumentException("DNA vacío");
        }

        int n = dna.length;

        for (String row : dna) {

            //filas null o vacías
            if (row == null || row.isBlank()) {
                throw new IllegalArgumentException("Fila vacía no permitida");
            }

            //NxN
            if (row.length() != n) {
                throw new IllegalArgumentException("La matriz debe ser de NxN");
            }

            //caracteres permitidos
            if (!row.matches("[ACGT]+")) {
                throw new IllegalArgumentException("ADN contiene caracteres inválidos");
            }
        }
    }
}
