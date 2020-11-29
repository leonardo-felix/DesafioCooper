package lpfx.desafio.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Data
public class Torcedor implements Serializable {

    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CPF(message = "Inválido")
    @NotNull
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @NotNull
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Email
    private String email;

    @OneToMany(mappedBy = "torcedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TorcedorTelefone> telefones;

    @OneToMany(mappedBy = "torcedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TorcedorEndereco> enderecos;
}
