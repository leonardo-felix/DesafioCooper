package lpfx.desafio.controllers;

import lombok.RequiredArgsConstructor;
import lpfx.desafio.exceptions.ApiErro;
import lpfx.desafio.model.Torcedor;
import lpfx.desafio.model.TorcedorTelefone;
import lpfx.desafio.model.Usuario;
import lpfx.desafio.repository.LogRepository;
import lpfx.desafio.repository.UsuarioRepository;
import lpfx.desafio.security.UserDetailsImpl;
import lpfx.desafio.services.impl.TorcedorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
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
    final TorcedorServiceImpl torcedorService;
    final LogRepository logRepository;
    final UsuarioRepository usuarioRepository;

    private void gerarLog(String descricao){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioRepository.findByLogin(userDetails.getUsername()).orElseThrow();
        logRepository.adicionarLog(descricao, usuario);
    }

    @GetMapping
    public List<Torcedor> todosTorcedores(){
        gerarLog("GET todosTorcedores");
        return torcedorService.todosTorcedores();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id){
        gerarLog("Excluindo torcedor id " + id);
        torcedorService.excluir(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torcedor> obterId(@PathVariable("id") Long id){
        gerarLog("Obtendo torcedor id " + id);
        Optional<Torcedor> optionalTorcedor = torcedorService.buscarPorId(id);
        if(optionalTorcedor.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(optionalTorcedor.get());
    }

    @GetMapping(value = "/obterPorCPF/{CPF}", produces = "application/json")
    public ResponseEntity<Torcedor> obterPorCPF(@PathVariable("CPF") @NotBlank @Size(min = 11, max = 11, message = "CPF não tem 11 dígitos") String CPF) {
        gerarLog("GET obterPorCPF: " + CPF);

        Optional<Torcedor> torcedorOptional = torcedorService.porCPF(CPF);

        if(torcedorOptional.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(torcedorOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> adicionarTorcedor(@Valid @RequestBody Torcedor torcedor){
        gerarLog("POST adicionarTorcedor: " + torcedor.getCpf());

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
    public ResponseEntity<?> alterar(@Valid @RequestBody Torcedor torcedor){
        gerarLog("POST alterTorcedor: {}" + torcedor.getCpf());
        return ResponseEntity.ok(torcedorService.adicionar(torcedor));
    }

}
