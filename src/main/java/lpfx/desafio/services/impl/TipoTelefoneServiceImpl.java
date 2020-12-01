package lpfx.desafio.services.impl;

import lombok.RequiredArgsConstructor;
import lpfx.desafio.model.TipoTelefone;
import lpfx.desafio.repository.TipoTelefoneRepository;
import lpfx.desafio.services.TipoTelefoneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoTelefoneServiceImpl implements TipoTelefoneService {
    final TipoTelefoneRepository tipoTelefoneRepository;

    @Override
    public List<TipoTelefone> todos() {
        return tipoTelefoneRepository.findAll();
    }
}
