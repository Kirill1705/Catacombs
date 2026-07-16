package thor.core.structure;

import lombok.Getter;
import lombok.Setter;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.PlayerSpawnPlaceInfo;

public class PlayerSpawnNode extends AbstractStructurePart {
    @Getter
    @Setter
    private boolean isBusy = false;
    @Getter
    private final PlayerSpawnPriority priority;

    public PlayerSpawnNode(Converter converter, PlayerSpawnPlaceInfo info) {
        super(converter, info.getPosition());
        this.priority = info.getPriority();
    }
}
