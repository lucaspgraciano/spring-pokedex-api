package br.com.tads.pokemon.repositories;

import br.com.tads.pokemon.entities.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbilityRepository extends JpaRepository<Ability, Integer> {
    Optional<Ability> findAbilityByName(String name);
}
