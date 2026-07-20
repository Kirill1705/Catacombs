package thor.core.port.input;

import lombok.Data;

@Data
public class MapPlaceOptions {
    private boolean fillStone = true;
    private boolean fillBedrock = true;
    private int spawnPlacesCount = 10;
}
