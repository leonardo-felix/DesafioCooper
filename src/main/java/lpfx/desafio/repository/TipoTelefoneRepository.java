package lpfx.desafio.repository;

import lpfx.desafio.model.TipoTelefone;
import lpfx.desafio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoTelefoneRepository extends JpaRepository<TipoTelefone, Long> {

}
