package lpfx.desafio

import lpfx.desafio.controllers.TorcedorController
import lpfx.desafio.model.Torcedor
import lpfx.desafio.services.TorcedorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.validation.ConstraintViolationException

@SpringBootTest
class DesafioApplicationTestes extends Specification{
    @Autowired(required = false)
    private TorcedorController torcedorController;

    @Autowired(required = false)
    private TorcedorService torcedorService;

    def "Testar Criacao contexto"() {
        expect: "O torcedorController ser criado"
        torcedorController
    }

    def "Torcedor OK"(){
        given:
        def torcedor = new Torcedor()
        torcedor.setNome("Leonardo Pires Felix")
        torcedor.setEmail("leonardo@piresfelix.com")
        torcedor.setCpf("02104110157")

        when:
            def torcedorGravado = torcedorService.adicionar(torcedor)

        then:
            notThrown ConstraintViolationException
            torcedorGravado.getId() > 0


    }

    def "CPF Invalido"(){
        given:
            def torcedor = new Torcedor()
            torcedor.setNome("Leonardo Pires Felix")
            torcedor.setEmail("leonardo@piresfelix.com")
            torcedor.setCpf("02104110155")

        when:
            torcedorService.adicionar(torcedor)

        then:
        ConstraintViolationException exception = thrown()
        exception
                .constraintViolations
                .stream()
                .anyMatch{cv -> (cv.propertyPath.toString() == "cpf") }
    }
}
