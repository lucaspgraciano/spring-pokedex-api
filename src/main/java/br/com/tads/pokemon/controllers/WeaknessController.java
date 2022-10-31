package br.com.tads.pokemon.controllers;

import br.com.tads.pokemon.dtos.PokemonTypeDto;
import br.com.tads.pokemon.services.WeaknessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/weakness")
public class WeaknessController {
    private final WeaknessService service;

    public WeaknessController(WeaknessService weaknessService) {
        this.service = weaknessService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PokemonTypeDto dto) {
        return ResponseEntity.ok(this.service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @Valid @RequestBody PokemonTypeDto dto) {
        return ResponseEntity.ok(this.service.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.service.deleteById(id));
    }
}
