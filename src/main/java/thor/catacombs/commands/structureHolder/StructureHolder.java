package thor.catacombs.commands.structureHolder;

import org.bukkit.structure.Structure;
import thor.catacombs.info.structure.interfaces.Nameable;
import thor.usefulUtils.utils.dataStructures.BlockLocation;

import java.util.List;

public interface StructureHolder<T extends Nameable> {
    boolean addStructure(Structure structure);
    boolean placeStructure(String name, BlockLocation location);
    boolean deleteStructure(String name);
    List<T> getStructures();
}
