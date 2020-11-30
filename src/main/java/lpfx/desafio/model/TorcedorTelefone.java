package lpfx.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lpfx.desafio.model.validators.TamanhoCelular;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@TamanhoCelular
public class TorcedorTelefone extends BaseModeloAbstrato {
    @JsonIgnore
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
