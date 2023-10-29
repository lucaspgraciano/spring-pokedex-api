package br.com.tads.pokemon.entities;

import br.com.tads.pokemon.dtos.TrainerDto;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Long experience;
    private Integer pokeballs;

    @ManyToMany
    @JoinTable(name = "trainer_pokemon",
        joinColumns = @JoinColumn(name = "trainer_id"),
        inverseJoinColumns = @JoinColumn(name = "pokemon_id"))
    private List<Pokemon> pokemons;

    public static Trainer assignToEntity(Trainer trainer, TrainerDto dto, List<Pokemon> pokemons) {
        trainer.name = dto.getName().toUpperCase();
        trainer.experience = dto.getExperience();
        trainer.pokeballs = dto.getPokeballs();
        trainer.pokemons = pokemons;
        return trainer;
    }
}
