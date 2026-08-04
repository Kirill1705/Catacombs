package thor.core.structure;

import lombok.RequiredArgsConstructor;
import thor.core.structure.manager.StructurePartsManager;

import java.util.List;

@RequiredArgsConstructor
public class AddInfoStructureVisitor implements StructureVisitor {
    private final List<StructurePartsManager> managers;

    @Override
    public void visit(Room room) {
        managers.forEach(manager -> manager.addFromRoomInfo(room.getRoomInfo(), room.getConverter()));
    }

    @Override
    public void visit(PartTunnel partTunnel) {
        // Implement later
    }
}
