package br.edu.tasima.pda.b.alerts.api.notify.serializer;

import br.edu.tasima.pda.b.alerts.api.notify.model.Team;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by Lucas_Gentile on 20/06/2018.
 */
public class TeamSerializer extends StdSerializer<Team> {

    public TeamSerializer() {
        this(Team.class);
    }

    public TeamSerializer(Class<Team> t) {
        super(t);
    }

    @Override
    public void serialize(Team value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", value.getName());
        gen.writeEndObject();
    }
}

