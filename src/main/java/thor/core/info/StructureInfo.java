package thor.core.info;

import lombok.Getter;
import thor.core.exception.DomainValidationException;
import thor.core.info.part.ChestInfo;
import thor.core.info.part.PlayerSpawnPlaceInfo;
import thor.core.info.part.Weight;
import thor.core.util.ConfUtils;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StructureInfo implements Weightable {
    @Getter
    private final Point size;
    private final Weight weight;
    @Getter
    private final String textId;
    @Getter
    private final Collection<ChestInfo> chests;
    @Getter
    private final Collection<PlayerSpawnPlaceInfo> playerSpawnPlaces;

    public StructureInfo(Point size, Weight weight, String textId, Collection<ChestInfo> chests, Collection<PlayerSpawnPlaceInfo> playerSpawnPlaces) {
        if (!size.more(new Point(0, 0, 0))) {
            throw new DomainValidationException(size);
        }
        if (textId == null || textId.isEmpty()) {
            throw new DomainValidationException(textId);
        }
        this.playerSpawnPlaces = ConfUtils.takeOrDefault(playerSpawnPlaces, List.of());
        this.size = size;
        this.weight = weight;
        this.textId = textId;
        this.chests = ConfUtils.takeOrDefault(Collections.unmodifiableCollection(chests), List.of());
    }

    @Override
    public Weight getWeight() {
        return weight;
    }
}
