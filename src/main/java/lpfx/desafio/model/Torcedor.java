package lpfx.desafio.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class Torcedor extends BaseModeloAbstrato {
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

    @OneToOne(cascade = CascadeType.ALL)
    private TorcedorEndereco endereco;

    /*
    @OneToMany(mappedBy = "torcedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TorcedorEndereco> enderecos;
     */
}
