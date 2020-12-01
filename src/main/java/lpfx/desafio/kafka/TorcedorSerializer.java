package lpfx.desafio.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lpfx.desafio.model.Torcedor;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class TorcedorSerializer implements Serializer<Torcedor> {
    @Override
    public void configure(Map configs, boolean isKey) {}

    @Override
    public byte[] serialize(String s, Torcedor torcedor) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            retVal = objectMapper.writeValueAsString(torcedor).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {}
}
