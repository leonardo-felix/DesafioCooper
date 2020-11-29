package lpfx.desafio.kafka;

public enum EnumTopicos {
    TORCEDOR_CADASTRADO("TorcedorCadastrado"), TORCEDOR_DESLIGADO("TorcedorDesligado");

    public final String mensagem;

    EnumTopicos(String mensagem){
        this.mensagem = mensagem;
    }
}
