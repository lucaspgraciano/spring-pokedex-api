package br.com.tads.pokemon.entities;

import br.com.tads.pokemon.dtos.AbilityDto;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Entity
@Table(name = "abilities")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Min(0)
    private Integer damage;

    @ManyToMany(mappedBy = "weaknesses", fetch = FetchType.LAZY)
    private List<Pokemon> abilities;

    public static Ability assignToEntity(Ability ability, AbilityDto dto) {
        ability.name = dto.getName().toUpperCase();
        ability.damage = dto.getDamage();
        return ability;
    }
}
