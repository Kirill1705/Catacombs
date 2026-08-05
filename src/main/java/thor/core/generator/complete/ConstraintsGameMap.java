package thor.core.generator.complete;

import lombok.extern.slf4j.Slf4j;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.Structure;
import thor.core.structure.create.StructureCreator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ConstraintsGameMap implements GameMap, StructureCreator {
    private final GameMap gameMap;

    private final Map<StructureCreator, Integer> constraints;
    private final StructureCreator creator;

    private StructureCreator lastUsed;

    public ConstraintsGameMap(GameMap gameMap, Map<StructureCreator, Integer> constraints, StructureCreator creator) {
        this.constraints = new HashMap<>(constraints);
        this.gameMap = gameMap;
        this.creator = creator;
    }

    @Override
    public void addStructure(Structure structure) {
        gameMap.addStructure(structure);

        if (lastUsed == null) {
            log.warn("Generator added structure before created it");
            return;
        }
        if (lastUsed == creator) return;

        constraints.put(lastUsed, constraints.get(lastUsed) - 1);
        if (constraints.get(lastUsed) <= 0) {
            constraints.remove(lastUsed);
        }
    }

    @Override
    public boolean canAddByField(Structure structure) {
        return gameMap.canAddByField(structure);
    }

    @Override
    public boolean canAddByOverlaps(Structure structure) {
        return gameMap.canAddByOverlaps(structure);
    }

    @Override
    public List<Structure> getAllStructures() {
        return gameMap.getAllStructures();
    }

    @Override
    public Point getSize() {
        return gameMap.getSize();
    }

    @Override
    public Structure create(Point position) {
        if (constraints.isEmpty()) {
            return createAndUpdateLast(creator, position);
        }
        StructureCreator creator = constraints.keySet().iterator().next();
        return createAndUpdateLast(creator, position);
    }

    private Structure createAndUpdateLast(StructureCreator creator, Point position) {
        Structure structure = creator.create(position);
        lastUsed = creator;
        return structure;
    }
}
