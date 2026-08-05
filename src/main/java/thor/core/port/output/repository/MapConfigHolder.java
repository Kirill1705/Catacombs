package thor.core.port.output.repository;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.port.mapping.dto.MapConfig;

public interface MapConfigHolder {
    Point catacombsMapSize();
    Point waterWorldSize();
    int roomsQuantity();

    int getWaterIslandsQuantity();
}
