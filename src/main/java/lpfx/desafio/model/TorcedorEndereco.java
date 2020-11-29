package lpfx.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class TorcedorEndereco extends BaseModeloAbstrato {

    /*
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "torcedor_id")
    Torcedor torcedor;
     */

    @OneToOne(optional = false)
    Torcedor torcedor;

    @NotBlank
    @Pattern(regexp = "^[\\d]{8}$", message = "CEP Inválido")
    @Column(nullable = false)
    String CEP;

    String logradouro;
    String complemento;
    String bairro;
    String cidade;

    @Column(nullable = false)
    String UF;
}
