package lpfx.desafio.exceptions;

import io.swagger.annotations.Api;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@RequiredArgsConstructor
@Data
public class ApiErro implements Serializable {
    final private HttpStatus status;
    final private String mensagem;

    public static ResponseEntity<ApiErro> montaResposta(ApiErro apiErro){
        return ResponseEntity
                .status(apiErro.status)
                .body(apiErro);
    }

    public static ApiErro BadRequest(final String mensagem){
        return new ApiErro(HttpStatus.BAD_REQUEST, mensagem);
    }

    public ResponseEntity<ApiErro> montaResposta(){
        return montaResposta(this);
    }
}
