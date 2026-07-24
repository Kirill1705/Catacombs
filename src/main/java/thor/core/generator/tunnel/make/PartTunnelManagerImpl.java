package thor.core.generator.tunnel.make;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import thor.core.generator.complete.MapField;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.generator.tunnel.convert.ConverterImpl;
import thor.core.info.TunnelInfo;
import thor.core.info.part.PartTunnelInfo;
import thor.core.info.part.TunnelType;
import thor.core.structure.Exit;
import thor.core.structure.PartTunnel;
import thor.core.structure.create.PartTunnelCreator;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PartTunnelManagerImpl implements PartTunnelManager {
    private final TunnelPartsDispenser dispenser;
    @Getter
    private final Point size;
    @Getter
    private final Converter converter;
    private final PartTunnelCreator creator;
    private final MapField field;

    private final Map<TunnelProgressInfo, TunnelInfo> tunnelInfoMap = new HashMap<>();
    private final Map<TunnelCreatorNode, TunnelNodeInfo> nodes = new HashMap<>();

    public PartTunnelManagerImpl(Exit first, Exit second, List<TunnelInfo> horizontal, List<TunnelInfo> vertical, TunnelPartsDispenser dispenser, PartTunnelCreator creator, MapField field) {
        this.dispenser = dispenser;
        this.creator = creator;
        this.field = field;
        this.size = first.getPosition().add(first.getOffset()).size((second.getPosition().add(second.getOffset())));
        converter = new ConverterImpl(first.getPosition().add(first.getOffset()), second.getPosition().add(second.getOffset()));
        tunnelInfoMap.put(new TunnelProgressInfo(TunnelProgress.START, TunnelType.HORIZONTAL), chooseTunnelInfoByName(first.getTunnels(), horizontal));
        tunnelInfoMap.put(new TunnelProgressInfo(TunnelProgress.START, TunnelType.VERTICAL), chooseTunnelInfoByName(first.getTunnels(), vertical));
        tunnelInfoMap.put(new TunnelProgressInfo(TunnelProgress.END, TunnelType.HORIZONTAL), chooseTunnelInfoByName(second.getTunnels(), horizontal));
        tunnelInfoMap.put(new TunnelProgressInfo(TunnelProgress.END, TunnelType.VERTICAL), chooseTunnelInfoByName(second.getTunnels(), vertical));
        tunnelInfoMap.put(new TunnelProgressInfo(TunnelProgress.NEUTRAL, TunnelType.HORIZONTAL), getRandomNeutralTunnelInfo(horizontal));
        tunnelInfoMap.put(new TunnelProgressInfo(TunnelProgress.NEUTRAL, TunnelType.VERTICAL), getRandomNeutralTunnelInfo(vertical));

    }

    public boolean isUpsideDown() {
        return converter.convertVector(new Point(0, 1, 0)).y() == -1;
    }

    private TunnelInfo chooseTunnelInfoByName(@NotNull Collection<String> tunnelNames, List<TunnelInfo> tunnelInfos) {
        if (tunnelNames.isEmpty()) {
            return getRandomTunnelInfo(tunnelInfos);
        }
        List<TunnelInfo> filteredInfos = tunnelInfos.stream()
                .filter(tunnelInfo -> tunnelNames.contains(tunnelInfo.getTextId()))
                .toList();
        return getRandomTunnelInfo(filteredInfos);
    }

    private TunnelInfo getRandomNeutralTunnelInfo(List<TunnelInfo> infos) {
        List<TunnelInfo> filtered = infos.stream()
                .filter(TunnelInfo::isNeutral)
                .toList();
        return getRandomTunnelInfo(filtered);
    }

    private TunnelInfo getRandomTunnelInfo(List<TunnelInfo> infos) {
        return infos.get(randomIdx(infos.size()));
    }

    private int randomIdx(int size) {
        return (int) (Math.random() * size);
    }

    @Override
    public boolean canPlace(TunnelCreatorNode node) {
        PartTunnel partTunnel = create(node);
        return field.canPlaceByMap(partTunnel.toBox());
    }

    @Override
    public PartTunnel create(TunnelCreatorNode node) {
        TunnelProgress progress = dispenser.getProgress(size, node.getPosition());
        TunnelType type = TunnelType.fromOffset(node.getOffset());
        int idx = 0;
        if (node.getParent() != null && nodes.containsKey(node.getParent())) {
            TunnelNodeInfo parentInfo = nodes.get(node.getParent());
            if (parentInfo.progress == progress) {
                idx = parentInfo.idx + 1;
            }
        }
        else if (node.getParent() != null) {
            log.warn("NodeInfo not contains!!");
        }
        TunnelInfo info = tunnelInfoMap.get(new TunnelProgressInfo(progress, type));
        PartTunnelInfo partTunnelInfo = info.getByIdx(idx);
        nodes.put(node, new TunnelNodeInfo(idx, progress));
        return creator.create(node.getPosition(), node.getOffset(), partTunnelInfo, converter);
    }

    private record TunnelProgressInfo(TunnelProgress tunnelProgress, TunnelType type) {}

    private record TunnelNodeInfo(int idx, TunnelProgress progress) {}
}
