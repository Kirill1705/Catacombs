package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PlayerSpawnPlaceInfo;
import thor.core.structure.PartTunnel;
import thor.core.structure.PlayerSpawnNode;
import thor.core.structure.PlayerSpawnPriority;

import java.util.*;

public class PlayerSpawnManager extends AbstractStructurePartManager<PlayerSpawnPlaceInfo, PlayerSpawnNode> {


    public void addSpawnsBeforeCap(int capacity, List<PartTunnel> tunnels) {
        int count = capacity - getParts().size();
        if (count <= 0) return;
        if (count > tunnels.size()) {
            throw new IllegalStateException();
        }
        Collections.shuffle(tunnels);
        for (int i = 0; i < count; i++) {
            getParts().add(new PlayerSpawnNode(Converter.simple(tunnels.get(i).getAttachmentPoint()), new PlayerSpawnPlaceInfo(
                    PlayerSpawnPriority.lowest(),
                    new Point(0, 0, 0)
            )));
        }
    }

    public List<PlayerSpawnNode> getSortedNodes() {
        return getParts().stream()
                .sorted(Comparator.comparing(PlayerSpawnNode::getPriority))
                .toList();
    }

    @Override
    protected PlayerSpawnNode create(PlayerSpawnPlaceInfo info, Converter converter) {
        return new PlayerSpawnNode(converter, info);
    }
}
