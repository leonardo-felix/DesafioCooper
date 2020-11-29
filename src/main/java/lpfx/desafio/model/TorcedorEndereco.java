package lpfx.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Data
public class TorcedorEndereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "torcedor_id")
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
