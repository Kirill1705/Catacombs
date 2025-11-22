package thor.catacombs.commands.structureHolder;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.structure.Structure;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.usefulUtils.utils.StructureUtils;

public class RoomHolder extends StructureHolderImpl<RoomInfo> {
    public RoomHolder(Plugin plugin, World world, AttributeRegistry registry, InfoCreator<RoomInfo> creator) {
        super(plugin.getName().toLowerCase()+"_rooms", world, registry, creator);
    }

    @Override
    public boolean addStructure(Structure structure) {
        return loadStructure(structure, new UsualStructurePlacer(), true) != null;
    }
}
