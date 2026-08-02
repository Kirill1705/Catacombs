package thor.core.structure.manager;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.info.SignalType;
import thor.core.info.part.EffectInfo;
import thor.core.info.part.PartTunnelInfo;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.EffectNode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EffectNodeManager extends AbstractStructurePartManager<EffectInfo, EffectNode> implements SignalPartManager {
    @Override
    protected EffectNode create(EffectInfo info, Converter converter) {
        return new EffectNode(converter, info);
    }

    @Override
    protected Iterable<EffectInfo> extractFromRoomInfo(RoomInfo roomInfo) {
        return roomInfo.getEffects();
    }

    @Override
    protected Iterable<EffectInfo> extractFromPartTunnelInfo(PartTunnelInfo partTunnelInfo) {
        return List.of();
    }

    @Override
    public void onSignal(WorldAccessor accessor, UUID entityId, Point position, SignalType signalType) {
        List<EffectNode> nodes = getParts().stream()
                .filter(effectNode -> effectNode.getPosition().equals(position) && effectNode.getEffectInfo().signalType() == signalType)
                .toList();
        for (EffectNode node: nodes) {
            accessor.applyEffect(entityId, node.getEffectInfo().effect(), node.getEffectInfo().amplifier(), node.getEffectInfo().duration());
        }
    }
}
