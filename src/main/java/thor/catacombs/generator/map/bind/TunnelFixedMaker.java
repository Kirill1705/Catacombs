package thor.catacombs.generator.map.bind;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thor.catacombs.events.creators.PartTunnelCreator;
import thor.catacombs.generator.Exit;
import thor.catacombs.generator.map.ImmutableGraph;
import thor.catacombs.generator.structures.TunnelPart;
import thor.catacombs.info.structure.TunnelType;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public class TunnelFixedMaker implements TunnelMaker{
    private final TunnelMaker tunnelMaker;
    private final PartTunnelCreator creator;

    public TunnelFixedMaker(TunnelMaker tunnelMaker, PartTunnelCreator creator) {
        this.tunnelMaker = tunnelMaker;
        this.creator = creator;
    }

    @Override
    public @Nullable List<TunnelPart> tryToMakeTunnel(Exit first, Exit second, ImmutableGraph graph) {
        List<TunnelPart> parts = tunnelMaker.tryToMakeTunnel(first, second, graph);
        if (parts == null) return null;
        int size = parts.size();
        for (int i = 1; i < size; i++) {
            TunnelPart prev = parts.get(i-1);
            TunnelPart current = parts.get(i);
            AddTunnelsResult result = new AddTunnelsResult.Nothing();
            if (prev.getInfo().getType() == TunnelType.VERTICAL && current.getInfo().getType() != TunnelType.VERTICAL) {
                result = toAdd(prev, current, graph);
            }
            else if (current.getInfo().getType() == TunnelType.VERTICAL && prev.getInfo().getType() != TunnelType.VERTICAL) {
                result = toAdd(current, prev, graph);
            }
            if (result instanceof AddTunnelsResult.Failure)
                return null;
            if (result instanceof AddTunnelsResult.Success success) {
                parts.addAll(success.parts);
            }
        }
        return parts;
    }

    private @NotNull AddTunnelsResult toAdd(TunnelPart vertical, TunnelPart horizontal, ImmutableGraph graph) {
        BlockPosition posY = new Point(vertical.getPosition().x(), horizontal.getPosition().add(horizontal.getInfo().getAttachmentPoint()).y(), vertical.getPosition().z());
        BlockPosition posY1 = posY.add(new Point(0, 1, 0));
        TunnelPart partY = create(posY, vertical, graph);
        TunnelPart partY1 = create(posY1, vertical, graph);
        if (partY == null || partY1 == null)
            return new AddTunnelsResult.Failure();
        return new AddTunnelsResult.Success(List.of(partY, partY1));
    }

    private @Nullable TunnelPart create(BlockPosition position, TunnelPart vertical, ImmutableGraph graph) {
        TunnelPart part = creator.create(vertical.getInfo(), position);
        if (!graph.canPlaceByMap(part.getOffsetBox()))
            return null;
        return part;
    }

    private abstract static class AddTunnelsResult {
        public static final class Success extends AddTunnelsResult {
            final List<TunnelPart> parts;

            public Success(List<TunnelPart> parts) {
                this.parts = parts;
            }
        }

        public static final class Nothing extends AddTunnelsResult {}

        public static final class Failure extends AddTunnelsResult {}
    }
}
