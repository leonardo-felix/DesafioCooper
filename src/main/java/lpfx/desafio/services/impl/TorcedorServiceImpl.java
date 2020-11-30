package lpfx.desafio.services.impl;

import lombok.RequiredArgsConstructor;
import lpfx.desafio.kafka.EnumTopicos;
import lpfx.desafio.kafka.KafkaSender;
import lpfx.desafio.model.Torcedor;
import lpfx.desafio.repository.TorcedorRepository;
import lpfx.desafio.services.TorcedorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TorcedorServiceImpl implements TorcedorService {
    final TorcedorRepository torcedorRepository;
    final KafkaSender kafkaSender;

    @Override
    @Transactional(readOnly = true)
    public Optional<Torcedor> porCPF(final String CPF) {
        return torcedorRepository.porCPF(CPF);
    }

    @Override
    @Transactional
    public Torcedor adicionar(final Torcedor torcedor) {
        // A geração do modelo via objeto recebido não atribui os objetos filhos, é necessário fazer isso manualmente

        if(!CollectionUtils.isEmpty(torcedor.getTelefones())){
            torcedor.getTelefones().forEach(tt -> tt.setTorcedor(torcedor));
        }
        if(torcedor.getEndereco() != null){
            torcedor.getEndereco().setTorcedor(torcedor);
        }
        if(torcedor.getId() == null){
            kafkaSender.sendMessage("Usuário de CPF " + torcedor.getCpf() + "Cadastrado", EnumTopicos.TORCEDOR_CADASTRADO.topico);
        }
        return torcedorRepository.adicionar(torcedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Torcedor> todosTorcedores() {
        return torcedorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCPF(final String CPF) {
        return torcedorRepository.existePorCPF(CPF);
    }

    @Override
    public Optional<Torcedor> buscarPorId(final Long id) {
        return torcedorRepository.findById(id);
    }
}
