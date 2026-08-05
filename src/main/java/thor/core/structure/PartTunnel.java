package thor.core.structure;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Ladder;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PartTunnelInfo;
import thor.core.info.part.TunnelType;
import thor.core.port.output.StructureManager;
import thor.core.port.output.WorldAccessor;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public class PartTunnel extends AbstractStructure {
    @Getter
    private final Point attachmentPoint;
    private final TunnelType type;
    private final int idx;

    public PartTunnel(Converter converter, PartTunnelInfo partTunnelInfo, boolean rotated) {
        super(converter, partTunnelInfo, rotated);
        this.attachmentPoint = converter.toOld(partTunnelInfo.getAttachmentPoint());
        this.type = partTunnelInfo.getType();
        this.idx = partTunnelInfo.getIdx();
    }

    public boolean isVertical() {
        return type == TunnelType.VERTICAL;
    }

    public void afterPlace(WorldAccessor accessor, ImmutableLocation location) {
        if (isVertical()) {
            Block block = accessor.getBlockAt(attachmentPoint.add(location.position()), location.worldName());
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
    public void place(WorldAccessor accessor, StructureManager structureManager, ImmutableLocation location) {
        String path = structureManager.getTunnelPartPath(getTextId(), idx);
        accessor.placeStructure(path, getPosition().add(location.position()), isRotated(), location.worldName());
    }

    @Override
    public void accept(StructureVisitor visitor) {
        visitor.visit(this);
    }
}
