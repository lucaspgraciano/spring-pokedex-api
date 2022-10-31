package br.com.tads.pokemon.repositories;

import br.com.tads.pokemon.entities.Weakness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeaknessRepository extends JpaRepository<Weakness, Integer> {
    Optional<Weakness> findWeaknessByName(String name);
}
