package thor.core.port.mapping;

import lombok.Data;

@Data
public class MapPlaceOptions {
    private boolean fillStone = true;
    private boolean fillBedrock = true;
    private int spawnPlacesCount = 10;
}
