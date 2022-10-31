package br.com.tads.pokemon.controllers;

import br.com.tads.pokemon.dtos.PokemonDto;
import br.com.tads.pokemon.services.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {
    private final PokemonService service;

    public PokemonController(PokemonService pokemonService) {
        this.service = pokemonService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() { return ResponseEntity.ok(this.service.findAll()); }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PokemonDto dto) {
        return ResponseEntity.ok(this.service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @Valid @RequestBody PokemonDto dto) {
        return ResponseEntity.ok(this.service.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.service.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.service.findById(id));
    }

    @GetMapping("/by-type")
    public ResponseEntity<?> findByType(@RequestParam String type) {
        return ResponseEntity.ok(this.service.findPokemonsByTypes(type));
    }

    @GetMapping("/most-catched")
    public ResponseEntity<?> findTenMostCatchedPokemons() {
        return ResponseEntity.ok(this.service.findTenMostCatchedPokemons());
    }
}
