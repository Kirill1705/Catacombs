package thor.core.info.part;

import lombok.Getter;
import thor.core.exception.DomainValidationException;
import thor.core.info.StructureInfo;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Collection;

public class PartTunnelInfo extends StructureInfo {
    @Getter
    private final Point attachmentPoint;
    @Getter
    private final TunnelType type;
    @Getter
    private final int idx;

    public PartTunnelInfo(String name, Weight weight, Point attachmentPoint, TunnelType type, Point size, Collection<ChestInfo> chests, Collection<PlayerSpawnPlaceInfo> playerSpawnPlaces, int idx) {
        super(size, weight, name, chests, playerSpawnPlaces);
        this.idx = idx;
        if (!attachmentPoint.less(size) || !attachmentPoint.moreOrEquals(new Point(0, 0, 0)))
            throw new DomainValidationException(attachmentPoint);
        this.attachmentPoint = attachmentPoint;
        this.type = type;
    }
}
