package lpfx.desafio.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class TipoTelefone extends BaseModeloAbstrato {
    @Column(nullable = false, length = 50)
    private String descricao;

    @Column(nullable = false)
    boolean celular;
}
