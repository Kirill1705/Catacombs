package thor.core.structure;

import lombok.Getter;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.SignalType;
import thor.core.info.part.TeleportInfo;

public class Teleport extends AbstractStructurePart {
    @Getter
    private final SignalType type;

    @Getter
    private final Point place;
    @Getter
    private final Point direction;

    public Teleport(StructurePartPlaceInfo placeInfo, TeleportInfo teleportInfo) {
        super(placeInfo, teleportInfo.trigger());
        place = placeInfo.mapPosition().add(placeInfo.converter().toOld(teleportInfo.place()));
        type = teleportInfo.signalType();
        this.direction = teleportInfo.direction();
    }
}
