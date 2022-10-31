package br.com.tads.pokemon.controllers;

import br.com.tads.pokemon.dtos.TrainerDto;
import br.com.tads.pokemon.services.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService service;

    public TrainerController(TrainerService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<?> findAll() { return ResponseEntity.ok(this.service.findAll()); }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TrainerDto dto) {
        return ResponseEntity.ok(this.service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id, @Valid @RequestBody TrainerDto dto) {
        return ResponseEntity.ok(this.service.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.service.deleteById(id));
    }
}
