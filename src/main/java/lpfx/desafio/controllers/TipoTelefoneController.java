package lpfx.desafio.controllers;

import lombok.RequiredArgsConstructor;
import lpfx.desafio.model.TipoTelefone;
import lpfx.desafio.model.Torcedor;
import lpfx.desafio.services.TipoTelefoneService;
import lpfx.desafio.services.impl.TorcedorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@RestControllerAdvice
@RequestMapping("/tipoTelefone")
@RequiredArgsConstructor
@Validated
@RolesAllowed({"ADMIN"})
public class TipoTelefoneController {
    private final Logger logger = LoggerFactory.getLogger(TipoTelefoneController.class);
    final TipoTelefoneService tipoTelefoneService;

    @GetMapping
    public List<TipoTelefone> todos(){
        logger.debug("GET todosTipoTelefone");
        return tipoTelefoneService.todos();
    }
}
