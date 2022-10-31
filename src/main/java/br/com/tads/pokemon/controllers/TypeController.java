package br.com.tads.pokemon.controllers;

import br.com.tads.pokemon.dtos.PokemonTypeDto;
import br.com.tads.pokemon.services.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/type")
public class TypeController {
    private final TypeService service;

    public TypeController(TypeService typeService) { this.service = typeService; }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PokemonTypeDto dto) {
        return ResponseEntity.ok(this.service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @Valid @RequestBody PokemonTypeDto dto) {
        return ResponseEntity.ok(this.service.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.service.deleteById(id));
    }
}
