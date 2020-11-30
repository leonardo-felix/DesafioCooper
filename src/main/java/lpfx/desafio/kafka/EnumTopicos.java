package lpfx.desafio.kafka;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EnumTopicos {
    TORCEDOR_CADASTRADO("TorcedorCadastrado"),
    TORCEDOR_DESLIGADO("TorcedorDesligado");

    public final String topico;
}
