package Hypercell.BlogApp.model.Serializer;

import Hypercell.BlogApp.model.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomUserSerializer extends StdSerializer<List<User>> {

    public CustomUserSerializer(){
        this(null);
    }
    public CustomUserSerializer(Class<List<User>> t) {
        super(t);
    }

    @Override
    public void serialize(List<User> friends, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        List<Integer> friend_ids = new ArrayList<>();
        for (User friend:friends) {
            friend_ids.add(friend.getId());
        }
        jsonGenerator.writeObject(friend_ids);
    }
}
