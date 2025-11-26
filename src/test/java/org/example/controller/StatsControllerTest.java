package org.example.controller;

import org.example.service.StatsService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StatsController.class)
class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    @Test
    void retornaStatsCorrectos() throws Exception {

        // datos mockeados
        when(statsService.countMutants()).thenReturn(3L);
        when(statsService.countHumans()).thenReturn(5L);
        when(statsService.getRatio()).thenReturn(0.6);

        mockMvc.perform(
                        get("/stats")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(3))
                .andExpect(jsonPath("$.count_human_dna").value(5))
                .andExpect(jsonPath("$.ratio").value(0.6));
    }
}



