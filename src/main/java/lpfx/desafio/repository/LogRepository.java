package lpfx.desafio.repository;

import lpfx.desafio.model.Log;
import lpfx.desafio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    default void adicionarLog(String descricao, Usuario usuario){
        Log log = new Log();
        log.setDescricao(descricao);
        log.setUsuario(usuario);
        log.setData(LocalDateTime.now());

        save(log);
    }
}
