package br.com.tads.pokemon.entities;

import br.com.tads.pokemon.dtos.PokemonDto;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "pokemons")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Long height;
    private Long weight;
    private Integer health;
    private Integer attack;
    private Integer defense;
    private Integer specialAttack;
    private Integer specialDefense;
    private Integer speed;

    @ManyToMany
    @JoinTable(name = "pokemon_type",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<Type> types;

    @ManyToMany
    @JoinTable(name = "pokemon_weakness",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "weakness_id"))
    private List<Weakness> weaknesses;

    @ManyToMany
    @JoinTable(name = "pokemon_ability",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "ability_id"))
    private List<Ability> abilities;

    @ManyToMany(mappedBy = "pokemons", fetch = FetchType.LAZY)
    private List<Trainer> trainers;

    public static Pokemon assignToEntity(Pokemon pokemon, PokemonDto dto, List<Type> types, List<Weakness> weaknesses, List<Ability> abilities) {
        pokemon.name = dto.getName().toUpperCase();
        pokemon.height = dto.getHeight();
        pokemon.weight = dto.getWeight();
        pokemon.health = dto.getHealth();
        pokemon.attack = dto.getAttack();
        pokemon.defense = dto.getDefense();
        pokemon.specialAttack = dto.getSpecialAttack();
        pokemon.specialDefense = dto.getSpecialDefense();
        pokemon.speed = dto.getSpeed();
        pokemon.types = types;
        pokemon.weaknesses = weaknesses;
        pokemon.abilities = abilities;
        return pokemon;
    }
}
