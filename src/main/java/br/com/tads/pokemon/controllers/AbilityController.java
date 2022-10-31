package br.com.tads.pokemon.controllers;

import br.com.tads.pokemon.dtos.AbilityDto;
import br.com.tads.pokemon.services.AbilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ability")
public class AbilityController {
    private final AbilityService service;

    public AbilityController(AbilityService abilityService) { this.service = abilityService; }

    @GetMapping
    public ResponseEntity<?> findAll() { return ResponseEntity.ok(this.service.findAll()); }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AbilityDto dto) {
        return ResponseEntity.ok(this.service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @Valid @RequestBody AbilityDto dto) {
        return ResponseEntity.ok(this.service.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.service.deleteById(id));
    }
}
