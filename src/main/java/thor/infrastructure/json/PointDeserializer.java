package thor.infrastructure.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import thor.usefulUtils.utils.dataStructures.Point;

import java.io.IOException;
import java.util.Arrays;

public class PointDeserializer extends JsonDeserializer<Point> {
    @Override
    public Point deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        int[] coords = p.readValueAs(int[].class);
        if (coords.length != 3) {
            throw new IOException("The given coords array " + Arrays.toString(coords) + " is invalid");
        }
        return new Point(coords[0], coords[1], coords[2]);
    }
}
