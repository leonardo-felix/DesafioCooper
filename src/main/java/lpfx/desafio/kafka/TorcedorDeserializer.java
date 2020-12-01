package lpfx.desafio.kafka;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lpfx.desafio.model.Torcedor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class TorcedorDeserializer implements Deserializer<Torcedor> {
    @Override
    public void configure(Map configs, boolean isKey) {}

    @Override
    public Torcedor deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Torcedor torcedor = null;
        try{
            torcedor = mapper.readValue(bytes, Torcedor.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return torcedor;
    }

    @Override
    public void close() {}
}
