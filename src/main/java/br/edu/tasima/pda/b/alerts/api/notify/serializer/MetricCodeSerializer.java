package br.edu.tasima.pda.b.alerts.api.notify.serializer;

import br.edu.tasima.pda.b.alerts.api.notify.model.MetricCode;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by Lucas_Gentile on 20/06/2018.
 */
public class MetricCodeSerializer extends StdSerializer<MetricCode> {

    public MetricCodeSerializer() {
        this(MetricCode.class);
    }

    public MetricCodeSerializer(Class<MetricCode> t) {
        super(t);
    }

    @Override
    public void serialize(MetricCode value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("metricCode", value.getCode());
        gen.writeEndObject();
    }
}

