package thor.infrastructure.repositories;

import lombok.Data;
import ru.vikhrenko.serverUtils.reload.YamlAbstractReloadable;
import thor.core.exception.DomainValidationException;
import thor.core.port.output.repository.MapConfigHolder;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public class MapConfigHolderImpl extends YamlAbstractReloadable<MapConfigHolderImpl.Config> implements MapConfigHolder {

    public MapConfigHolderImpl() {
        super(new Config(), Config.class);
    }

    @Override
    public Point catacombsMapSize() {
        return getOptions().catacombsMapSize;
    }

    @Override
    public Point waterWorldSize() {
        return getOptions().waterMapSize;
    }

    @Override
    public int roomsQuantity() {
        return getOptions().catacombsRoomsQuantity;
    }

    @Override
    public int getWaterIslandsQuantity() {
        return getOptions().waterIslandsQuantity;
    }

    @Override
    public void validate() {
        if (!catacombsMapSize().more(new Point(0, 0, 0))) {
            throw new DomainValidationException(getOptions().catacombsMapSize);
        }
        if (!waterWorldSize().more(new Point(0, 0, 0))) {
            throw new DomainValidationException(getOptions().waterMapSize);
        }
        if (roomsQuantity() <= 0) {
            throw new DomainValidationException(roomsQuantity());
        }
    }

    @Data
    public static class Config {
        private Point catacombsMapSize = new Point(256, 64, 256);

        private Point waterMapSize = new Point(128, 64, 128);

        private int catacombsRoomsQuantity = 200;

        private int waterIslandsQuantity = 30;
    }
}
