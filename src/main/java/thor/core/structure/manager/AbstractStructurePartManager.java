package thor.core.structure.manager;

import lombok.Getter;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.info.IslandInfo;
import thor.core.info.RoomInfo;
import thor.core.structure.Island;
import thor.core.structure.Room;
import thor.core.structure.StructurePart;
import thor.core.structure.StructurePartPlaceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class AbstractStructurePartManager<T, U extends StructurePart> implements StructurePartsManager {
    @Getter
    private final List<U> parts = new ArrayList<>();

    @Override
    public void addFromRoom(Room room, Point mapPosition, String worldName) {
        for (T info: extractFromIslandInfo(room.getRoomInfo())) {
            parts.add(create(info, new StructurePartPlaceInfo(room.getConverter(), mapPosition, worldName)));
        }
    }

    @Override
    public void addFromIslandInfo(Island island, Point mapPosition, String worldName) {
        for (T info: extractFromIslandInfo(island.getIslandInfo())) {
            parts.add(create(info, new StructurePartPlaceInfo(island.getConverter(), mapPosition, worldName)));
        }
    }

    protected abstract U create(T info, StructurePartPlaceInfo placeInfo);

    protected abstract Iterable<T> extractFromIslandInfo(IslandInfo roomInfo);

    protected List<U> findPart(Point position, String worldName) {
        return filterByPosition(position, worldName).toList();
    }

    protected List<U> findPart(Point position, String worldName, Predicate<U> condition) {
        return filterByPosition(position, worldName)
                .filter(condition)
                .toList();
    }

    private Stream<U> filterByPosition(Point position, String worldName) {
        return parts.stream()
                .filter(part -> part.getPosition().equals(position) && part.getWorldName().equals(worldName));
    }
}
