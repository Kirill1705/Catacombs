package thor.core.structure;

public interface StructureVisitor {
    void visit(Room room);

    void visit(PartTunnel partTunnel);
}
