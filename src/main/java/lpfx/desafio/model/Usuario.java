package lpfx.desafio.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Usuario extends BaseModeloAbstrato {
    @NotBlank
    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank
    @Column(nullable = false)
    private String senha;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private UsuarioPapel usuarioPapel;
}
