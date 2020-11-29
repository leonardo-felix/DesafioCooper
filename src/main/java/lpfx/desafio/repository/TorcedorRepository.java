package lpfx.desafio.repository;

import lpfx.desafio.model.Torcedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TorcedorRepository extends JpaRepository<Torcedor, Long> {
    default Optional<Torcedor> porCPF(String CPF){
        return findByCpfEquals(CPF);
    }

    default Torcedor adicionar(Torcedor torcedor){
        return save(torcedor);
    }

    default boolean existePorCPF(String CPF) {
        return existsByCpf(CPF);
    }

    Optional<Torcedor> findByCpfEquals(String CPF);
    boolean existsByCpf(String CPF);

}
