package thor.core.structure;

import lombok.Getter;

public class PortalCountVisitor implements StructureVisitor {
    @Getter
    private int count = 0;

    @Override
    public void visit(Room room) {
        count+=room.getRoomInfo().getPortals().size();
    }

    @Override
    public void visit(PartTunnel partTunnel) {

    }

    @Override
    public void visit(Island island) {
        count+=island.getIslandInfo().getPortals().size();
    }
}
