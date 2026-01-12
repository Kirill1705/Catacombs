package thor.catacombs.generator.map;

import thor.catacombs.generator.structures.utils.GameWorldAccessor;

public interface AfterPlacing {
    void afterPlace(GameWorldAccessor accessor);
}
