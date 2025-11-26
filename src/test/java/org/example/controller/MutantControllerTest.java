package org.example.controller;

import org.example.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    private String toJson(String... dna) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"dna\":[");
        for (int i = 0; i < dna.length; i++) {
            sb.append("\"").append(dna[i]).append("\"");
            if (i < dna.length - 1) sb.append(",");
        }
        sb.append("]}");
        return sb.toString();
    }

    @Test
    void retorna200CuandoEsMutante() throws Exception {

        when(mutantService.isMutant(any(String[].class)))
                .thenReturn(true);

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"))
        ).andExpect(status().isOk());
    }

    @Test
    void retorna403CuandoNoEsMutante() throws Exception {

        when(mutantService.isMutant(any(String[].class)))
                .thenReturn(false);

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson("ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"))
        ).andExpect(status().isForbidden());
    }

    @Test
    void retorna400CuandoJsonEsInvalido() throws Exception {

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\":123}")  // JSON inválido: dna no es un array
        ).andExpect(status().isBadRequest());
    }

    @Test
    void retorna400CuandoNoHayBody() throws Exception {

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
