package org.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.web.server.ResponseStatusException;
import org.example.validator.DnaValidator;


class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    @Test   //Mutante Horizontal
    void detectaMutanteHorizontal() {
        String[] dna = {
                "AAAACT",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }


    @Test   //Mutante Vertical
    void detectaMutanteVertical() {
        String[] dna = {
                "ATGCGA",
                "ATGTGC",
                "ATATGT",
                "ATAAGG",
                "ATCCTA",
                "ATCCTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test   //Mutante diagonal inferior
    void detectaMutanteDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CATGAC",
                "TTAAGT",
                "AAAAAG",
                "CCCAAA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test   //Mutante Diagonal Invertido
    void detectaMutanteDiagonalInvertido() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTGAAT",
                "AGAAGG",
                "CAACTA",
                "AACCTG"
        };

        assertTrue(detector.isMutant(dna));
    }

    @Test   //No mutante y 0 secuencias
    void noEsMutante() {
        String[] dna = {
                "TTGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test   //No mutante y 1 secuencia
    void tieneSolo1Secuencia_NoEsMutante() {
        String[] dna = {
                "AAAAGT",  // única secuencia AAAA
                "CGCGCG",
                "TCATGT",
                "AGTAGG",
                "CACCTA",
                "TCACTG"
        };

        assertFalse(detector.isMutant(dna));
    }

    @Test   //No Mutante con 1 secuencia vertical
    void tieneSolo1SecuenciaVertical_NoEsMutante() {
        String[] dna = {
                "ATGCAA",
                "ATGTGC",
                "ATATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test   //Mutante con secciones mixtas
    void detectaMutanteMixtoHorizontalVertical() {
        String[] dna = {
                "AAAAGT", // secuencia horizontal
                "ATGTGC",
                "ATATGT",
                "ATAAGG",
                "ATCCTA", // parte de secuencia vertical
                "ATCCTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test   //Mutante secuencia en borde infrerior
    void detectaMutanteEnBordeInferior() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "CCCCCC"
        };
        assertTrue(detector.isMutant(dna));
    }


    @Test   //Mutante secuencia en borde derecho
    void detectaMutanteEnBordeDerecho() {
        String[] dna = {
                "AGGGGA",  // secuencia horizontal al final
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test //ADN null
    void dnaNullDebeLanzarBadRequest() {
        assertThrows(ResponseStatusException.class, () -> {
            DnaValidator.validate(null);
        });
    }


    @Test   //ADN vacío
    void dnaVacioDebeSerHumano() {
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }

    @Test   //Filas DEsiguales
    void dnaConFilasDesiguales_NoEsMutante() {
        String[] dna = {
                "ATG",
                "CAGT",
                "TTAT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test   //Carácteres Inválidos
    void dnaConCaracteresInvalidos_NoEsMutante() {
        String[] dna = {
                "ATBZGA",
                "CXGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test   //Falta de filas
    void dnaUnaSolaFila_NoEsMutante() {
        String[] dna = {
                "AAAAAA"
        };
        assertFalse(detector.isMutant(dna));
    }


}
