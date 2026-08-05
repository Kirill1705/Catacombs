package thor.core.structure.create.builder;

import thor.core.port.output.WorldAccessor;

public interface WorldAccessorStage {
    PlayerTeleportatorStage withWorldAccessor(WorldAccessor accessor);
}
