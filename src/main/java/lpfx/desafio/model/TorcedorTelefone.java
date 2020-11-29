package lpfx.desafio.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table
@Data
public class TorcedorTelefone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotNull
    @ManyToOne(optional = false)
    Torcedor torcedor;

    @NotNull
    @ManyToOne(optional = false)
    TipoTelefone tipoTelefone;

    @NotBlank
    @Pattern(regexp = "^[0-9]{8,9}$")
    @Column(length = 9)
    String numero;

    @Column(nullable = false)
    boolean principal;
}
