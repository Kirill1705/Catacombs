package thor.core.structure;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import thor.core.structure.manager.StructurePartsManager;

import java.util.List;

@RequiredArgsConstructor
public class AddInfoStructureVisitor implements StructureVisitor {
    private final List<StructurePartsManager> managers;
    @Setter
    private ImmutableLocation location;

    @Override
    public void visit(Room room) {
        managers.forEach(manager -> manager.addFromRoom(room, location.position(), location.worldName()));
    }

    @Override
    public void visit(PartTunnel partTunnel) {
        // Implement later
    }

    @Override
    public void visit(Island island) {
        managers.forEach(manager -> manager.addFromIslandInfo(island, location.position(), location.worldName()));
    }
}
