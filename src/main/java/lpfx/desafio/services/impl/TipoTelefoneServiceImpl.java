package lpfx.desafio.services.impl;

import lombok.RequiredArgsConstructor;
import lpfx.desafio.model.TipoTelefone;
import lpfx.desafio.model.Torcedor;
import lpfx.desafio.repository.TipoTelefoneRepository;
import lpfx.desafio.repository.TorcedorRepository;
import lpfx.desafio.services.TipoTelefoneService;
import lpfx.desafio.services.TorcedorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoTelefoneServiceImpl implements TipoTelefoneService {
    final TipoTelefoneRepository tipoTelefoneRepository;

    @Override
    public List<TipoTelefone> todos() {
        return tipoTelefoneRepository.findAll();
    }
}
