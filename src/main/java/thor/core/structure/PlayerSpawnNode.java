package thor.core.structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PlayerSpawnPlaceInfo;


public class PlayerSpawnNode extends AbstractStructurePart {
    @Getter
    private final PlayerSpawnPriority priority;

    public PlayerSpawnNode(Converter converter, PlayerSpawnPlaceInfo info) {
        super(converter, info.getPosition());
        this.priority = info.getPriority();
    }
}
