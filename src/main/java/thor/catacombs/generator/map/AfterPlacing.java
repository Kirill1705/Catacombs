package thor.catacombs.generator.map;

import thor.usefulUtils.utils.dataStructures.BlockLocation;

public interface AfterPlacing {
    void afterPlace(BlockLocation location);
}
