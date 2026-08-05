package thor.core.structure.create;

import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableWorldBox;
import thor.core.port.mapping.dto.map.InteractiveGameMap;

import java.util.List;

public record InteractiveGameMapWithBoxes(InteractiveGameMap map, List<ImmutableWorldBox> boxes) {
}
