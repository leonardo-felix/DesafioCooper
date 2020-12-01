package lpfx.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class TorcedorEndereco extends BaseModeloAbstrato {
    @JsonIgnore
    @OneToOne(optional = false)
    Torcedor torcedor;

    @NotBlank
    @Pattern(regexp = "^[\\d]{8}$", message = "CEP Inválido")
    @Column(nullable = false)
    String CEP;

    String logradouro;
    String complemento;
    String bairro;
    String localidade;

    @Column(nullable = false)
    String UF;
}
