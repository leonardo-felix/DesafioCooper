package lpfx.desafio;

import lpfx.desafio.exceptions.ApiErro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<ApiErro> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){

        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(cv -> errors.add("Campo " + cv.getPropertyPath() + ": " + cv.getMessage()));

        return new ApiErro(HttpStatus.BAD_REQUEST, "Erro de validação: " + String.join(", ", errors)).montaResposta();
    }

}
