package lpfx.desafio.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class BaseModeloAbstrato implements Serializable {
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
