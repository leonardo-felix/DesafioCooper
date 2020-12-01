package lpfx.desafio;

import lpfx.desafio.exceptions.ApiErro;
import lpfx.desafio.exceptions.ErroInternoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ErroInternoException.class})
    ResponseEntity<ApiErro> handleConstraintViolationException(ErroInternoException ex){
        return new ApiErro(HttpStatus.BAD_REQUEST, ex.getMessage()).montaResposta();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    ResponseEntity<ApiErro> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request ){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        List<String> errors = new ArrayList<>();

        allErrors.forEach(objectError -> errors.add(objectError.getDefaultMessage()));

        return new ApiErro(HttpStatus.BAD_REQUEST, "Erro de validação: " + String.join(", ", errors)).montaResposta();
    }

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<ApiErro> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){

        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(cv -> errors.add("Campo " + cv.getPropertyPath() + ": " + cv.getMessage()));

        return new ApiErro(HttpStatus.BAD_REQUEST, "Erro de validação: " + String.join(", ", errors)).montaResposta();
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiErro> handleException(Exception ex){
        return new ApiErro(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()).montaResposta();
    }
}
