package thor.core.port.input;

import java.util.UUID;

public interface MapService {
    void removeMap(UUID mapId);
    /**
     * Generates new random game map
     * @return map UUID
     */
    UUID generateMap();
}
