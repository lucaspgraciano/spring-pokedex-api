package br.com.tads.pokemon.repositories;

import br.com.tads.pokemon.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Optional<Trainer> findTrainerByName(String name);

    @Query(value = "SELECT T.* FROM TRAINERS T JOIN TRAINER_POKEMON TP ON T.ID = TP.TRAINER_ID WHERE TP.POKEMON_ID LIKE :pokemonId", nativeQuery = true)
    List<Trainer> findAllByPokemonId(Integer pokemonId);
}
