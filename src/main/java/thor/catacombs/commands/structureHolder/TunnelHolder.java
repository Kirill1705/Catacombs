package thor.catacombs.commands.structureHolder;

import org.bukkit.World;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.plugin.Plugin;
import org.bukkit.structure.Structure;
import org.bukkit.util.BlockVector;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.structure.TunnelType;
import thor.catacombs.info.structure.interfaces.TunnelInfo;
import thor.usefulUtils.utils.StructureUtils;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Random;

public class TunnelHolder extends StructureHolderImpl<TunnelInfo> {
    public TunnelHolder(Plugin plugin, World world, AttributeRegistry registry, InfoCreator<TunnelInfo> creator) {
        super(plugin.getName().toLowerCase()+"_tunnels", world, registry, creator);
    }

    @Override
    public boolean addStructure(Structure structure) {
        TunnelInfo info = loadStructure(structure, new UsualStructurePlacer(), true);
        if (info==null)
            return false;
        if (info.getType() != TunnelType.VERTICAL) {
            TunnelInfo other = loadStructure(structure, new RotatedStructurePlacer(), false);
            return other!=null;
        }
        return true;
    }

    private static class RotatedStructurePlacer implements StructurePlacer {
        @Override
        public void place(Structure structure, BlockLocation location) {
            structure.place(location.add(new Point(structure.getSize().getBlockX()-1, 0, 0)).toLocation(), true, StructureRotation.CLOCKWISE_90, Mirror.NONE, 0, 1, new Random());
        }
        @Override
        public BlockPosition getSize(Structure structure) {
            BlockVector size = structure.getSize();
            return new Point(size.getBlockZ(), size.getBlockY(), size.getBlockX());
        }
    }
}
