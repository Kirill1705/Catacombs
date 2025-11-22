package thor.catacombs.generator.map.bind;

import thor.catacombs.generator.map.ImmutableGraph;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableOffsetBox;
import thor.usefulUtils.utils.dataStructures.Point;

public record GamePlaceChecker(Transformer transformer, ImmutableGraph graph, PartTunnelInfo info, PartTunnelInfo vertical) implements PlaceChecker {
    @Override
    public boolean canPlace(BlockPosition newPosition, boolean isVertical) {
        BlockPosition position = transformer.toOld(newPosition);
        PartTunnelInfo current = isVertical ? vertical : info;
        BlockPosition begin = position.subtract(current.getAttachmentPoint());
        return graph.canPlaceByMap(new ImmutableOffsetBox(begin, begin.add(current.getSize()).subtract(new Point(1, 1, 1))));
    }
}
