package springAngularApp.system.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JsonUtils {

    public static String toJSON(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter sWriter = new StringWriter();
        try {
            JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(sWriter);
            objectMapper.writeValue(jsonGenerator, o);
            return sWriter.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}