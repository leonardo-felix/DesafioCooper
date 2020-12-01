package lpfx.desafio.repository;

import lpfx.desafio.model.TipoTelefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTelefoneRepository extends JpaRepository<TipoTelefone, Long> {

}
