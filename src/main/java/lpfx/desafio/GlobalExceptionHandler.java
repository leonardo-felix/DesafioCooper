package lpfx.desafio;

import lpfx.desafio.exceptions.ApiErro;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<ApiErro> handleConstraintViolationException(ConstraintViolationException ex){

        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(cv -> errors.add("Campo " + cv.getPropertyPath() + ": " + cv.getMessage()));

        return new ApiErro(HttpStatus.BAD_REQUEST, "Erro de validação: " + String.join(", ", errors)).montaResposta();
    }

}
