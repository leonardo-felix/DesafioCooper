package lpfx.desafio.services;

import lpfx.desafio.model.Torcedor;

import java.util.List;
import java.util.Optional;

public interface TorcedorService {
    Optional<Torcedor> porCPF(String CPF);
    Torcedor adicionar(Torcedor torcedor);
    List<Torcedor> todosTorcedores();
    boolean existePorCPF(String CPF);
}
