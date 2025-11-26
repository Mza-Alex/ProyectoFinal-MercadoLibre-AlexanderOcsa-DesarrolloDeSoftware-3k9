package org.example.controller;

import org.example.dto.DnaRequest;
import org.example.service.MutantService;
import org.example.validator.DnaValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MutantController {

    private final MutantService service;

    public MutantController(MutantService service) {
        this.service = service;
    }

    @PostMapping("mutant")
    public ResponseEntity<Void> isMutant(@RequestBody DnaRequest dna) {

        String[] dnaArray = dna.getDna().toArray(new String[0]);

        // Validación Centralizada
        try {
            DnaValidator.validate(dnaArray);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400
        }

        // Detección Mutante
        boolean isMutant = service.isMutant(dnaArray);

        if (isMutant) {
            return ResponseEntity.ok().build();     // 200
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403
        }
    }
}
