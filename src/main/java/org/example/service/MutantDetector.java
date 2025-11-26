package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    private static final int SEQ = 4;

    public boolean isMutant(String[] dna) {
        int n = dna.length;
        char[][] m = new char[n][n];

        //PAra que se convierta en matriz
        for (int i = 0; i < n; i++) {
            m[i] = dna[i].toCharArray();
        }

        int count = 0;

        // Direcciones
        int[][] dirs = {
                {0, 1},   //derecha
                {1, 0},   //abajo
                {1, 1},   //diagonal de izq. a der.
                {1, -1}   //diagonal de der. a izq.
        };

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                for (int[] d : dirs) {
                    if (haySecuencia(m, i, j, d[0], d[1], n)) {
                        count++;
                        if (count >= 2) return true; // mutante
                    }
                }
            }
        }

        return false; //para que sea humano
    }

    private boolean haySecuencia(char[][] m, int i, int j, int dx, int dy, int n) {
        char c = m[i][j];

        for (int k = 1; k < SEQ; k++) {
            int x = i + dx * k;
            int y = j + dy * k;

            if (x < 0 || x >= n || y < 0 || y >= n) return false;
            if (m[x][y] != c) return false;
        }

        return true;
    }
}
