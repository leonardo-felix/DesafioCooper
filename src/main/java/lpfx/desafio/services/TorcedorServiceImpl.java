package lpfx.desafio.services;

import lombok.RequiredArgsConstructor;
import lpfx.desafio.model.Torcedor;
import lpfx.desafio.repository.TorcedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TorcedorServiceImpl implements TorcedorService {
    final TorcedorRepository torcedorRepository;

    @Override
    public Optional<Torcedor> porCPF(String CPF) {
        return torcedorRepository.porCPF(CPF);
    }

    @Override
    public Torcedor adicionar(Torcedor torcedor) {
        return torcedorRepository.adicionar(torcedor);
    }

    @Override
    public List<Torcedor> todosTorcedores() {
        return torcedorRepository.findAll();
    }

    @Override
    public boolean existePorCPF(String CPF) {
        return torcedorRepository.existePorCPF(CPF);
    }
}
