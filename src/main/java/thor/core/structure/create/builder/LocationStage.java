package thor.core.structure.create.builder;

import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import thor.core.structure.manager.PlayerTeleportator;

public interface LocationStage {
    PlaceStage withLocation(ImmutableLocation location);
}
