package thor.core.structure;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Ladder;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PartTunnelInfo;
import thor.core.info.part.TunnelType;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public class PartTunnelImpl extends AbstractStructure implements PartTunnel{
    @Getter
    private final Point attachmentPoint;
    private final TunnelType type;
    private final int idx;

    public PartTunnelImpl(Converter converter, PartTunnelInfo partTunnelInfo, boolean rotated) {
        super(converter, partTunnelInfo, rotated);
        this.attachmentPoint = converter.toOld(partTunnelInfo.getAttachmentPoint());
        this.type = partTunnelInfo.getType();
        this.idx = partTunnelInfo.getIdx();
    }

    @Override
    public boolean isVertical() {
        return type == TunnelType.VERTICAL;
    }

    @Override
    public void afterPlace(WorldAccessor accessor) {
        if (isVertical()) {
            Block block = accessor.getBlockAt(attachmentPoint);
            BlockFace[] checkFaces = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
            for (BlockFace face : checkFaces) {
                Block possibleWall = block.getRelative(face);
                if (possibleWall.getType().isSolid()) {
                    block.setType(Material.LADDER, false);
                    if (block.getBlockData() instanceof Ladder ladderData) {
                        ladderData.setFacing(face.getOppositeFace());
                        block.setBlockData(ladderData, true);
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected void place(WorldAccessor accessor, StructureManager structureManager, boolean rotated) {
        String path = structureManager.getTunnelPartPath(getTextId(), idx);
        accessor.placeStructure(path, getPosition(), rotated);
    }
}
