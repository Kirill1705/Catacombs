package thor.core.info.part;

import lombok.Getter;
import thor.core.exception.DomainValidationException;
import thor.core.info.StructureInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.Collection;

public class PartTunnelInfo extends StructureInfo {
    @Getter
    private final BlockPosition attachmentPoint;
    @Getter
    private final TunnelType type;
    @Getter
    private final int idx;

    public PartTunnelInfo(String name, Weight weight, BlockPosition attachmentPoint, TunnelType type, BlockPosition size, Collection<ChestInfo> chests, Collection<PlayerSpawnPlaceInfo> playerSpawnPlaces, int idx) {
        super(size, weight, name, chests, playerSpawnPlaces);
        this.idx = idx;
        if (attachmentPoint.notLess(size))
            throw new DomainValidationException(attachmentPoint);
        this.attachmentPoint = attachmentPoint;
        this.type = type;
    }
}
