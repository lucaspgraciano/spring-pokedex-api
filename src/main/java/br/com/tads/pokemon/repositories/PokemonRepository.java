package br.com.tads.pokemon.repositories;

import br.com.tads.pokemon.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    Optional<Pokemon> findPokemonByName(String name);

    @Query(value = "SELECT P.* FROM POKEMONS P JOIN POKEMON_TYPE PT ON P.ID = PT.POKEMON_ID WHERE PT.TYPE_ID LIKE :typeId", nativeQuery = true)
    List<Pokemon> findAllByTypeId(Integer typeId);

    @Query(value = "SELECT P.* FROM POKEMONS P JOIN POKEMON_WEAKNESS PW ON P.ID = PW.POKEMON_ID WHERE PW.WEAKNESS_ID LIKE :weaknessId", nativeQuery = true)
    List<Pokemon> findAllByWeaknessId(Integer weaknessId);

    @Query(value = "SELECT P.* FROM POKEMONS P JOIN POKEMON_ABILITY PA ON P.ID = PA.POKEMON_ID WHERE PA.ABILITY_ID LIKE :abilityId", nativeQuery = true)
    List<Pokemon> findAllByAbilityId(Integer abilityId);

    @Query(value = "SELECT COUNT(1) AS TOTAL, P.* FROM TRAINER_POKEMON TP JOIN POKEMONS P ON TP.POKEMON_ID = P.ID GROUP BY P.ID ORDER BY TOTAL DESC LIMIT 10", nativeQuery = true)
    List<Pokemon> findTenMostCatchedPokemons();
}
