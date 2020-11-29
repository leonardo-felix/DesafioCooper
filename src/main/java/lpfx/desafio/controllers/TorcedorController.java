package lpfx.desafio.controllers;

import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lpfx.desafio.GlobalExceptionHandler;
import lpfx.desafio.exceptions.ApiErro;
import lpfx.desafio.model.Torcedor;
import lpfx.desafio.model.TorcedorTelefone;
import lpfx.desafio.services.TorcedorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@RestControllerAdvice
@RequestMapping("/torcedor")
@RequiredArgsConstructor
@Validated
@RolesAllowed({"ADMIN"})
public class TorcedorController {
    final TorcedorServiceImpl torcedorService;

    @GetMapping("/todos")
    public List<Torcedor> todosTorcedores(){
        return torcedorService.todosTorcedores();
    }

    @GetMapping(value = "/obterPorCPF/{CPF}", produces = "application/json")
    public ResponseEntity<Torcedor> obterPorCPF(@PathVariable("CPF") @NotBlank @Size(min = 11, max = 11, message = "CPF não tem 11 dígitos") String CPF) {
        Optional<Torcedor> torcedorOptional = torcedorService.porCPF(CPF);

        if(torcedorOptional.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(torcedorOptional.get());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> adicionarTorcedor(@RequestBody Torcedor torcedor){
        // Validações básicas

        if(torcedorService.existePorCPF(torcedor.getCpf())){
            return new ApiErro(HttpStatus.CONFLICT, "CPF já existe na base").montaResposta();
        }

        if(CollectionUtils.isEmpty(torcedor.getEnderecos())){
            return ApiErro.BadRequest("É necessário ao menos um endereço").montaResposta();
        }

        if(CollectionUtils.isEmpty(torcedor.getTelefones())){
            return ApiErro.BadRequest("É necessário ao menos um telefone").montaResposta();
        } else if (torcedor.getTelefones().stream().noneMatch(TorcedorTelefone::isPrincipal)) {
            return ApiErro.BadRequest("É necessário ao menos um telefone principal").montaResposta();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(torcedorService.adicionar(torcedor));
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody Torcedor torcedor){
        return ResponseEntity.ok(torcedorService.adicionar(torcedor));
    }

}
