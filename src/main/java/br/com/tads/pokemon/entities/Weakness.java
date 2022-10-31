package br.com.tads.pokemon.entities;

import br.com.tads.pokemon.dtos.PokemonTypeDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "weaknesses")
public class Weakness {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "weaknesses", fetch = FetchType.LAZY)
    private List<Pokemon> weaknesses;

    public static Weakness assignToEntity(Weakness weakness, PokemonTypeDto dto) {
        weakness.name = dto.getName().toUpperCase();
        return weakness;
    }
}
