package thor.core.structure.create.builder;

import thor.core.structure.manager.config.PlacePartManager;

public interface PlaceStage {
    BoxStage withPlaceManager(PlacePartManager placeManager);
}
