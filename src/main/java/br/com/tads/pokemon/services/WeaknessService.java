package br.com.tads.pokemon.services;

import br.com.tads.pokemon.dtos.PokemonTypeDto;
import br.com.tads.pokemon.dtos.WeaknessDto;
import br.com.tads.pokemon.entities.Weakness;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.PokemonRepository;
import br.com.tads.pokemon.repositories.WeaknessRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.tads.pokemon.dtos.WeaknessDto.assignToDto;
import static br.com.tads.pokemon.entities.Weakness.assignToEntity;

@Service
public class WeaknessService {
    private final WeaknessRepository weaknessRepository;
    private final PokemonRepository pokemonRepository;

    public WeaknessService(WeaknessRepository weaknessRepository, PokemonRepository pokemonRepository) {
        this.weaknessRepository = weaknessRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public List<WeaknessDto> findAll() {
        return this.weaknessRepository.findAll().stream()
                .map(WeaknessDto::assignToDto)
                .collect(Collectors.toList());
    }

    public WeaknessDto create(PokemonTypeDto dto) {
        this.doesWeaknessAlreadyExists(dto.getName().toUpperCase());
        Weakness weakness = assignToEntity(new Weakness(), dto);
        return assignToDto(this.weaknessRepository.save(weakness));
    }

    public WeaknessDto updateById(Integer id, PokemonTypeDto dto) {
        Weakness weaknessToBeUpdate = this.findWeaknessById(id);
        if (!weaknessToBeUpdate.getName().equals(dto.getName().toUpperCase()))
            this.doesWeaknessAlreadyExists(dto.getName().toUpperCase());
        Weakness weakness = assignToEntity(weaknessToBeUpdate, dto);
        return assignToDto(this.weaknessRepository.save(weakness));
    }

    public WeaknessDto deleteById(Integer id) {
        this.doesHavePokemonWithWeakness(id);
        Weakness weaknessToBeDelete = this.findWeaknessById(id);
        WeaknessDto dto = assignToDto(weaknessToBeDelete);
        this.weaknessRepository.delete(weaknessToBeDelete);
        return dto;
    }

    private void doesWeaknessAlreadyExists(String weaknessName) {
        if (this.weaknessRepository.findWeaknessByName(weaknessName).isPresent())
            throw new ConflictException("Fraqueza %s ja cadastrada", weaknessName);
    }

    private Weakness findWeaknessById(Integer id) {
        return this.weaknessRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fraqueza com id %s nao encontrada", id));
    }

    private void doesHavePokemonWithWeakness(Integer weaknessId) {
        if (!this.pokemonRepository.findAllByWeaknessId(weaknessId).isEmpty())
            throw new ConflictException("Fraqueza com id %s esta sendo utilizada em um pokemon", weaknessId);
    }
}
