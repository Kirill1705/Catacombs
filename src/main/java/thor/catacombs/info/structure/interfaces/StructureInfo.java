package thor.catacombs.info.structure.interfaces;

import org.bukkit.structure.Structure;
import thor.catacombs.info.block.ChestInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.List;

public interface StructureInfo {
    List<ChestInfo> getChestsInfo();
    Structure getStructure();
    BlockPosition getSize();
}
