package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private MutantService service;

    @Mock
    private MutantDetector detector;

    //ADN mutante
    private static final String[] MUTANT_DNA = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
    };

    //ADN Humano
    private static final String[] HUMAN_DNA = {
            "ATGCGA",
            "CAGTGC",
            "TTATTT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
    };

    @Test
    void nuevoAdnMutante_devuelveTrueYGuardaEnBd() {
        when(repository.findByHash(anyString()))
                .thenReturn(Optional.empty());

        boolean result = service.isMutant(MUTANT_DNA);

        assertTrue(result);
        verify(repository).save(argThat(r ->
                r.isMutant() && r.getHash() != null && !r.getHash().isBlank()
        ));
    }

    @Test
    void nuevoAdnHumano_devuelveFalseYGuardaEnBd() {
        when(repository.findByHash(anyString()))
                .thenReturn(Optional.empty());

        boolean result = service.isMutant(HUMAN_DNA);

        assertFalse(result);
        verify(repository).save(argThat(r ->
                !r.isMutant() && r.getHash() != null && !r.getHash().isBlank()
        ));
    }

    @Test
    void adnYaProcesado_noVuelveAGuardar() {
        DnaRecord record = new DnaRecord();
        record.setHash("hash-ya-existente");
        record.setMutant(true);

        when(repository.findByHash(anyString()))
                .thenReturn(Optional.of(record));

        boolean result = service.isMutant(MUTANT_DNA);

        assertTrue(result);
        verify(repository, never()).save(any(DnaRecord.class));
    }

    @Test
    void adnHumanoYaProcesado_noVuelveAGuardar() {
        DnaRecord record = new DnaRecord();
        record.setHash("hash-humano");
        record.setMutant(false);

        when(repository.findByHash(anyString()))
                .thenReturn(Optional.of(record));

        boolean result = service.isMutant(HUMAN_DNA);

        assertFalse(result);
        verify(repository, never()).save(any(DnaRecord.class));
    }

    @Test
    void hashGenerado_esConsistenteYSeUsaParaBuscar() {
        when(repository.findByHash(anyString()))
                .thenReturn(Optional.empty());

        service.isMutant(MUTANT_DNA);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(repository).findByHash(captor.capture());

        String hashUsado = captor.getValue();

        assertNotNull(hashUsado);
        assertFalse(hashUsado.isEmpty());
        assertEquals(64, hashUsado.length()); // SHA-256 => 64 chars hex
    }

    @Test
    void guardaMutanteCuandoNoExiste() {

        when(repository.findByHash(anyString()))
                .thenReturn(Optional.empty());

        boolean result = service.isMutant(MUTANT_DNA);

        assertTrue(result);

        verify(repository).save(argThat(record ->
                record.isMutant()
                        && record.getHash() != null
        ));
    }


    @Test
    void guardaHumanoCuandoNoExiste() {

        when(repository.findByHash(anyString()))
                .thenReturn(Optional.empty());

        boolean result = service.isMutant(HUMAN_DNA);

        assertFalse(result);

        verify(repository).save(argThat(record ->
                !record.isMutant()
                        && record.getHash() != null
        ));
    }

    @Test
    void guardaSoloUnaVezCuandoNoExisteEnBD() {

        when(repository.findByHash(anyString()))
                .thenReturn(Optional.empty());

        service.isMutant(MUTANT_DNA);

        verify(repository, times(1)).save(any(DnaRecord.class));
    }


    @Test
    void lanzaExcepcionSiRepositoryFalla() {

        when(repository.findByHash(anyString()))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> service.isMutant(MUTANT_DNA)
        );
    }



}
