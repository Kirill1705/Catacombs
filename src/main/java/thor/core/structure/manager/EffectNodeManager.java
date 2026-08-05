package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.IslandInfo;
import thor.core.info.SignalType;
import thor.core.info.part.EffectInfo;
import thor.core.info.part.PartTunnelInfo;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.EffectNode;
import thor.core.structure.StructurePartPlaceInfo;

import java.util.List;
import java.util.UUID;

public class EffectNodeManager extends AbstractStructurePartManager<EffectInfo, EffectNode> implements SignalPartManager {
    @Override
    protected EffectNode create(EffectInfo info, StructurePartPlaceInfo converter) {
        return new EffectNode(converter, info);
    }

    @Override
    protected Iterable<EffectInfo> extractFromIslandInfo(IslandInfo roomInfo) {
        return roomInfo.getEffects();
    }

    @Override
    public void onSignal(WorldAccessor accessor, UUID entityId, Point position, SignalType signalType, String worldName) {
        List<EffectNode> nodes = findPart(position, worldName, effectNode -> effectNode.getEffectInfo().signalType() == signalType);
        for (EffectNode node: nodes) {
            accessor.applyEffect(entityId, node.getEffectInfo().effect(), node.getEffectInfo().amplifier(), node.getEffectInfo().duration());
        }
    }
}
