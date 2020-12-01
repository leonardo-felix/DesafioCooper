package lpfx.desafio.services.impl;

import lombok.RequiredArgsConstructor;
import lpfx.desafio.exceptions.ErroInternoException;
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

        Torcedor torcedorRetorno = torcedorRepository.adicionar(torcedor);
        kafkaSender.eventoCadastrado(torcedorRetorno);

        return torcedorRetorno;
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

    @Override
    @Transactional
    public void excluir(Long id) {
        Optional<Torcedor> torcedor = torcedorRepository.findById(id);

        if(torcedor.isEmpty())
            throw new ErroInternoException("ID não encontrado");

        torcedorRepository.deleteById(id);

        kafkaSender.eventoDesligado(torcedor.get());
    }
}
