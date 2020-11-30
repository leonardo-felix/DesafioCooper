package lpfx.desafio.services;

import lpfx.desafio.model.Torcedor;

import java.util.List;
import java.util.Optional;

public interface TorcedorService {
    Optional<Torcedor> porCPF(final String CPF);
    Torcedor adicionar(final Torcedor torcedor);
    List<Torcedor> todosTorcedores();
    boolean existePorCPF(final String CPF);
    Optional<Torcedor> buscarPorId(final Long id);
}
