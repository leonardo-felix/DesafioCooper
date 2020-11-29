package lpfx.desafio.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class TorcedorTelefone extends BaseModeloAbstrato {
    @NotNull
    @ManyToOne(optional = false)
    Torcedor torcedor;

    @NotNull
    @ManyToOne(optional = false)
    TipoTelefone tipoTelefone;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10,11}$")
    @Column(length = 11, nullable = false)
    String numero;

    @Column(nullable = false)
    boolean principal;
}
