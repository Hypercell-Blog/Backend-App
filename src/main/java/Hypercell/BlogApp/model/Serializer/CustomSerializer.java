package Hypercell.BlogApp.model.Serializer;

import Hypercell.BlogApp.model.Reactions;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomSerializer extends StdSerializer<List<Reactions>> {
    protected CustomSerializer(Class<List<Reactions>> t) {
        super(t);
    }
    public CustomSerializer(){
        this(null);
    }

    @Override
    public void serialize(List<Reactions> reactions, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    List<String> emojis=new ArrayList<>();
    for(Reactions reaction:reactions){
        emojis.add(reaction.getEmoji());
    }
        jsonGenerator.writeObject(emojis);
    }
}
