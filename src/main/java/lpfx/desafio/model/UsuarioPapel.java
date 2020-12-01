package lpfx.desafio.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class UsuarioPapel extends BaseModeloAbstrato {
    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private boolean admin;
}
