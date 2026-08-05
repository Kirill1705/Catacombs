package thor.core.structure;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;

public record StructurePartPlaceInfo(Converter converter, Point mapPosition, String worldName) {
}
