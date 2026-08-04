package thor.core.generator.tunnel.make;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.structure.PartTunnel;

import java.util.*;
import java.util.stream.Stream;

public enum TunnelCreator {
    VALUE;
    public List<PartTunnel> tryCreateTunnel(PartTunnelManager tunnelManager, Point size, Point sourceOffset, Point destOffset) {
        if (!isOffset(sourceOffset) || !isOffset(destOffset) || !size.more(new Point(0, 0, 0)) || !checkOffsets(sourceOffset, destOffset)) {
            return null;
        }
        TunnelCreatorNode node = bfs(tunnelManager, size, sourceOffset, destOffset);
        if (node == null) {
            return null;
        }
        List<PartTunnel> result = new ArrayList<>();
        while (node != null) {
            result.add(tunnelManager.create(node));
            node = node.getParent();
        }
        return result;
    }

    private TunnelCreatorNode bfs(PartTunnelManager tunnelManager, Point size, Point sourceOffset, Point destOffset) {
        Queue<TunnelCreatorNode> queue = new LinkedList<>();
        TunnelCreatorNode firstNode = createFirstNode(sourceOffset, destOffset);
        if (!tunnelManager.canPlace(firstNode)) {
            return null;
        }
        queue.add(firstNode);
        Set<Vertex> visited = new HashSet<>();
        visited.add(new Vertex(new Point(0, 0, 0), firstNode.getStatus()));
        Point destination = size.subtract(new Point(1, 1, 1));
        while (!queue.isEmpty()) {
            TunnelCreatorNode node = queue.remove();
            if (node.getPosition().equals(destination) && node.getOffset().multiply(-1).equals(destOffset)) {
                return node;
            }
            Collection<TunnelCreatorNode> neighbors = getNeighbors(node, destination, destOffset).stream()
                    .filter(neighbor ->
                            neighbor.getPosition().moreOrEquals(new Point(0, 0, 0)) &&
                            neighbor.getPosition().lessOrEquals(destination) &&
                            !visited.contains(new Vertex(neighbor.getPosition(), neighbor.getStatus())) &&
                            !(destOffset.equals(new Point(0, -1, 0)) && neighbor.getStatus() != TunnelCreatorNodeStat.VERTICAL && neighbor.getPosition().y() != destination.y() && neighbor.getPosition().x() == destination.x() && neighbor.getPosition().z() == destination.z()) &&
                            tunnelManager.canPlace(neighbor)
                            )
                    .toList();
            for (TunnelCreatorNode neighbor: neighbors) {
                queue.add(neighbor);
                visited.add(new Vertex(neighbor.getPosition(), neighbor.getStatus()));
            }
        }
        return null;
    }

    private boolean isOffset(Point position) {
        Point abs = position.abs();
        return abs.sumXYZ() == 1 && (abs.x() == 1 || abs.y() == 1 || abs.z() == 1);
    }

    private TunnelCreatorNode createFirstNode(Point sourceOffset, Point destOffset) {
        TunnelCreatorNodeStat status = getFirstStatus(sourceOffset, destOffset);
        return new TunnelCreatorNode(new Point(0, 0, 0), status, null, Stream.of(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1))
                .filter(type -> type.equals(sourceOffset))
                .findAny().get());
    }

    private TunnelCreatorNodeStat getFirstStatus(Point sourceOffset, Point destOffset) {
        if (sourceOffset.equals(new Point(0, 1, 0))) {
            return TunnelCreatorNodeStat.VERTICAL;
        }
        else {
            if (destOffset.equals(new Point(0, -1, 0))) {
                return TunnelCreatorNodeStat.ROTATION_DISABLED;
            }
            else {
                return TunnelCreatorNodeStat.ROTATION_ENABLED;
            }
        }
    }

    private boolean checkOffsets(Point sourceOffset, Point destOffset) {
        return sourceOffset.moreOrEquals(new Point(0, 0, 0)) && destOffset.lessOrEquals(new Point(0, 0, 0));
    }

    private Collection<TunnelCreatorNode> getNeighbors(TunnelCreatorNode node, Point dest, Point destOffset) {
        List<TunnelCreatorNode> result = new ArrayList<>(getOffsets(node.getOffset())
                .stream()
                .map(offset -> new TunnelCreatorNode(node, offset, node.getOffset()))
                .toList());
        if (node.getStatus() == TunnelCreatorNodeStat.VERTICAL) {
            if (node.getVerticalCount().canRotate()) {
                result.addAll(getOffsetAfterVertical().stream()
                        .map(offset -> new TunnelCreatorNode(node, offset.subtract(new Point(0, 1, 0)), TunnelCreatorNodeStat.ROTATION_DISABLED, offset))
                        .toList());
            }
        }
        else if (node.getStatus() == TunnelCreatorNodeStat.ROTATION_ENABLED) {
            result.add(new TunnelCreatorNode(node, node.getOffset(), TunnelCreatorNodeStat.VERTICAL, new Point(0, 1, 0)));
        }
        else {
            if (destOffset.abs().equals(new Point(0, 1, 0)) && dest.x() == node.getPosition().x() + node.getOffset().x() && dest.z() == node.getPosition().z() + node.getOffset().z() && !node.getOffset().equals(new Point(0, 1, 0))) {
                result.add(new TunnelCreatorNode(node, node.getOffset(), TunnelCreatorNodeStat.VERTICAL, new Point(0, 1, 0)));
            }
        }
        return result;
    }

    private List<Point> getOffsets(Point offset) {
        if (offset.equals(new Point(0, 1, 0))) {
            return Collections.singletonList(new Point(0, 1, 0));
        } else if (offset.equals(new Point(1, 0, 0))) {
            return List.of(new Point(1, 0, 0), new Point(1, 1, 0), new Point(1, 0, 1), new Point(1, 1, 1));
        } else if (offset.equals(new Point(0, 0, 1))) {
            return List.of(new Point(0, 0, 1), new Point(1, 0, 1), new Point(0, 1, 1), new Point(1, 1, 1));
        }
        throw new IllegalArgumentException();
    }

    private List<Point> getOffsetAfterVertical() {
        return List.of(new Point(1, 0, 0), new Point(0, 0, 1));
    }

    private record Vertex(Point position, TunnelCreatorNodeStat status) {}
}
