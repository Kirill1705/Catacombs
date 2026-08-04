package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.MapPlaceOptions;
import thor.core.port.output.WorldAccessor;
import thor.core.port.output.WorldAccessorCreator;
import thor.core.structure.PlacePartResult;

import java.util.Optional;

public interface PlacePartManager {
    Optional<PlacePartResult> place(WorldAccessorCreator accessorCreator, String worldName, Point position, MapPlaceOptions options);
}
