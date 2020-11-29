package lpfx.desafio.controllers;

import lombok.RequiredArgsConstructor;
import lpfx.desafio.exceptions.ApiErro;
import lpfx.desafio.model.Torcedor;
import lpfx.desafio.model.TorcedorTelefone;
import lpfx.desafio.services.impl.TorcedorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;


@RestControllerAdvice
@RequestMapping("/torcedor")
@RequiredArgsConstructor
@Validated
@RolesAllowed({"ADMIN"})
public class TorcedorController {
    private final Logger logger = LoggerFactory.getLogger(TorcedorController.class);
    final TorcedorServiceImpl torcedorService;

    @GetMapping
    public List<Torcedor> todosTorcedores(){
        logger.debug("GET todosTorcedores");
        return torcedorService.todosTorcedores();
    }

    @GetMapping(value = "/obterPorCPF/{CPF}", produces = "application/json")
    public ResponseEntity<Torcedor> obterPorCPF(@PathVariable("CPF") @NotBlank @Size(min = 11, max = 11, message = "CPF não tem 11 dígitos") String CPF) {
        logger.debug("GET obterPorCPF: {}", CPF);

        Optional<Torcedor> torcedorOptional = torcedorService.porCPF(CPF);

        if(torcedorOptional.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(torcedorOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> adicionarTorcedor(@RequestBody Torcedor torcedor){
        logger.debug("POST adicionarTorcedor: {}", torcedor);

        // Validações básicas
        if(torcedorService.existePorCPF(torcedor.getCpf())){
            return new ApiErro(HttpStatus.CONFLICT, "CPF já existe na base").montaResposta();
        }

        if(torcedor.getEndereco() == null){
            return ApiErro.BadRequest("É necessário endereço").montaResposta();
        }

        if(CollectionUtils.isEmpty(torcedor.getTelefones())){
            return ApiErro.BadRequest("É necessário ao menos um telefone").montaResposta();
        } else if (torcedor.getTelefones().stream().noneMatch(TorcedorTelefone::isPrincipal)) {
            return ApiErro.BadRequest("É necessário ao menos um telefone principal").montaResposta();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(torcedorService.adicionar(torcedor));
    }

    @PutMapping
    public ResponseEntity<?> alterar(@RequestBody Torcedor torcedor){
        logger.debug("POST adicionarTorcedor: {}", torcedor);
        return ResponseEntity.ok(torcedorService.adicionar(torcedor));
    }

}
