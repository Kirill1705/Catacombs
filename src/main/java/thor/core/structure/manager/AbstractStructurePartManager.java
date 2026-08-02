package thor.core.structure.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.info.part.PartTunnelInfo;
import thor.core.structure.AbstractStructurePart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractStructurePartManager<T, U> implements StructurePartsManager {
    @Getter
    private final List<U> parts = new ArrayList<>();

    @Override
    public void addFromRoomInfo(RoomInfo roomInfo, Converter converter) {
        for (T info: extractFromRoomInfo(roomInfo)) {
            parts.add(create(info, converter));
        }
    }

    @Override
    public void addFromPartTunnelInfo(PartTunnelInfo partTunnelInfo, Converter converter) {
        for (T info: extractFromPartTunnelInfo(partTunnelInfo)) {
            parts.add(create(info, converter));
        }
    }

    protected abstract U create(T info, Converter converter);

    protected abstract Iterable<T> extractFromRoomInfo(RoomInfo roomInfo);

    protected abstract Iterable<T> extractFromPartTunnelInfo(PartTunnelInfo partTunnelInfo);
}
