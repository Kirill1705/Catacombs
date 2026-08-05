package thor.core.structure;

import lombok.Getter;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.info.part.Dimension;
import thor.core.info.part.PortalInfo;

import java.util.Set;
import java.util.stream.Collectors;

public class Portal implements StructurePart {
    @Getter
    private final Set<Point> blocks;
    @Getter
    private final Point backPosition;
    @Getter
    private final Dimension dimension;
    @Getter
    private final String worldName;

    public Portal(StructurePartPlaceInfo placeInfo, PortalInfo info) {
        this.backPosition = placeInfo.mapPosition().add(placeInfo.converter().toOld(info.backPosition()));
        this.blocks = info.portals().stream()
                .map(point -> placeInfo.mapPosition().add(placeInfo.converter().toOld(point)))
                .collect(Collectors.toSet());
        this.dimension = info.dimension();
        this.worldName = placeInfo.worldName();
    }

    @Override
    public Point getPosition() {
        return backPosition;
    }
}
