package org.example.validator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class DnaValidatorTest {

    @Test
    void validDna_noLanzaExcepcion() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        assertDoesNotThrow(() -> DnaValidator.validate(dna));
    }

    @Test
    void dnaNuloOLargoCero_lanzaBadRequest() {

        String[] dna = null;

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> DnaValidator.validate(dna)
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void filaNulaOVacia_lanzaIllegalArgument() {
        String[] dna = {
                "ATGCGA",
                "",
                "TTATGT"
        };

        assertThrows(IllegalArgumentException.class,
                () -> DnaValidator.validate(dna));
    }

    @Test
    void matrizNoEsNxN_lanzaIllegalArgument() {   //El n es de 3 pero las filas son de 6
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT"
        };

        assertThrows(IllegalArgumentException.class,
                () -> DnaValidator.validate(dna));
    }

    @Test
    void contieneCaracteresInvalidos_lanzaIllegalArgument() {   //Hay una X en fila 3 columna 3
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTXTGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        assertThrows(IllegalArgumentException.class,
                () -> DnaValidator.validate(dna));
    }
}
