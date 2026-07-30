package thor.infrastructure.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public class ModuleCreator {
    public SimpleModule pointModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Point.class, new PointSerializer());
        module.addDeserializer(Point.class, new PointDeserializer());
        return module;
    }
}
