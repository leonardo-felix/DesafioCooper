package lpfx.desafio.kafka;

import lombok.RequiredArgsConstructor;
import lpfx.desafio.model.Torcedor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, Torcedor> kafkaTemplate;

    public void eventoCadastrado(Torcedor torcedor){
        kafkaTemplate.send(EnumTopicos.TORCEDOR_CADASTRADO.topico, torcedor);
    }

    public void eventoDesligado(Torcedor torcedor){
        kafkaTemplate.send(EnumTopicos.TORCEDOR_CADASTRADO.topico, torcedor);
    }
}

