package thor.core.structure;

import lombok.Getter;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.SignalType;
import thor.core.info.part.ArenaButtonInfo;

public class ArenaButton extends AbstractStructurePart {
    @Getter
    private final SignalType signalType;
    @Getter
    private final Point backPosition;

    public ArenaButton(StructurePartPlaceInfo placeInfo, ArenaButtonInfo arenaButtonInfo) {
        super(placeInfo, arenaButtonInfo.position());
        signalType = arenaButtonInfo.signalType();
        backPosition = placeInfo.mapPosition().add(placeInfo.converter().toOld(arenaButtonInfo.backPosition()));
    }
}
