package thor.catacombs.generator.map.bind;

import com.google.common.base.Preconditions;
import thor.catacombs.events.creators.PartTunnelCreator;
import thor.catacombs.generator.ChanceGenerator;
import thor.catacombs.generator.Exit;
import thor.catacombs.generator.RandomGenerator;
import thor.catacombs.generator.map.ImmutableGraph;
import thor.catacombs.generator.structures.TunnelPart;
import thor.catacombs.info.block.ExitInfo;
import thor.catacombs.info.structure.TunnelType;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.catacombs.info.structure.interfaces.TunnelInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class GameTunnelMaker implements TunnelMaker {
    private final Map<TunnelType, RandomGenerator<TunnelInfo>> infoMap;
    private final PartTunnelCreator creator;

    public GameTunnelMaker(Collection<TunnelInfo> infos, PartTunnelCreator creator) {
        this.infoMap = infos.stream()
                .collect(Collectors.groupingBy(
                        TunnelInfo::getType,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                ChanceGenerator::new
                        )
                ));
        Preconditions.checkArgument(infos.size() >= 3, "Not all direction of tunnels found!");
        this.creator = creator;
    }

    private boolean isVertical(BlockPosition current, BlockPosition prev) {
        return current.subtract(prev).abs().equals(new Point(0, 1, 0));
    }

    private List<TunnelPart> getTunnel(Node current, TunnelInfo vertical, TunnelInfo info, Transformer transformer) {
        List<TunnelPart> parts = new ArrayList<>();
        BlockPosition prev = null;
        while (current != null) {
            TunnelInfo currentInfo = current.vertical() || prev!=null && isVertical(current.position(), prev) ? vertical : info;
            PartTunnelInfo partTunnelInfo = currentInfo.getByIdx(current.idx());
            BlockPosition position = transformer.toOld(current.position());
            TunnelPart part = creator.create(partTunnelInfo, position.subtract(partTunnelInfo.getAttachmentPoint()));
            parts.add(part);
            prev = current.position;
            current = current.parent;
        }
        return parts;
    }

    private boolean canBind(Transformer transformer, BlockPosition direction, BlockPosition begin, BlockPosition end) {
        begin = transformer.convertVector(begin);
        end = transformer.convertVector(end);
        return (begin.equals(new Point(0, 1, 0)) || begin.equals(direction)) && (end.equals(new Point(0, -1, 0)) || end.equals(direction.multiply(-1)));
    }

    private boolean canBind(Exit begin, Exit end) {
        if (begin.getInfo().getType() == ExitInfo.ExitType.VERTICAL && end.getInfo().getType() == ExitInfo.ExitType.VERTICAL) {
            BlockPosition box = end.getPosition().subtract(begin.getPosition()).abs();
            return box.x() > 1 || box.z() > 1 || (box.x() == 0 && box.z() == 0);
        }
        return true;
    }

    @Override
    public @Nullable List<TunnelPart> tryToMakeTunnel(Exit exitBegin, Exit exitEnd, ImmutableGraph graph) {
        Map<TunnelType, TunnelInfo> infoMap = this.infoMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getRandom()
                ));
        if (!canBind(exitBegin, exitEnd)) {
            return null;
        }
        BlockPosition begin = exitBegin.getPosition().add(exitBegin.getInfo().getOffset());
        BlockPosition end = exitEnd.getPosition().add(exitEnd.getInfo().getOffset());
        Transformer transformer = new Transformer(begin, end);
        BlockPosition size = transformer.toNew(end);
        DirectionCalculator calculator = new DirectionCalculator(size);
        if (!canBind(transformer, calculator.getDirection(), exitBegin.getInfo().getOffset(), exitEnd.getInfo().getOffset()))
            return null;
        TunnelInfo vertical = infoMap.get(TunnelType.VERTICAL);
        TunnelInfo info = calculator.getDirection().abs().equals(TunnelType.X.getZeroMask()) ? infoMap.get(TunnelType.X) : infoMap.get(TunnelType.Z);
        Point zero = new Point(0, 0, 0);
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(zero, isVertical(begin, exitBegin.getPosition()), 0, null));
        Set<BlockPosition> visited = new HashSet<>();
        visited.add(zero);
        PlaceChecker checker = new GamePlaceChecker(transformer, graph, info.getByIdx(0), vertical.getByIdx(0));
        Neighbors neighborsGiver = new GameNeighbors(new NeighborsImpl(new DirectionCalculator(size), exitBegin.getInfo().getOffset(), exitEnd.getInfo().getOffset()), checker);
        if (!checker.canPlace(zero, exitBegin.getInfo().getOffset().abs().equals(new Point(0, 1, 0))))
            return null;
        Node result = null;
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            if (node.position.equals(size)) {
                result = node;
                break;
            }
            Collection<BlockPosition> neighbors = neighborsGiver.getNeighbors(node.position);
            for (BlockPosition position : neighbors) {
                if (visited.contains(position))
                    continue;
                boolean isVertical = isVertical(position, node.position);
                int idx = isVertical == node.vertical() ? node.idx + 1 : 0;
                queue.add(new Node(position, isVertical, idx, node));
                visited.add(position);
            }
        }
        if (result == null)
            return null;
        return getTunnel(result, vertical, info, transformer);
    }

    private record Node(BlockPosition position, boolean vertical, int idx, Node parent) {}
}
