package lpfx.desafio

import lpfx.desafio.model.Torcedor

class models {
    static Torcedor torcedorValido() {
        def torcedor = new Torcedor()
        torcedor.setNome("Leonardo Pires Felix")
        torcedor.setEmail("leonardo@piresfelix.com")
        torcedor.setCpf("02104110157")
        return torcedor
    }

    static Torcedor torcedorCPFInvalido(){
        def torcedor = torcedorValido()
        torcedor.setCpf("02104110155")
        return torcedor;
    }
}
