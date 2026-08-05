package thor.core.structure.create.builder;

import thor.core.structure.manager.PlayerTeleportator;

public interface PlayerTeleportatorStage {
    InteractivePartStage withPlayerTeleportator(PlayerTeleportator playerTeleportator);
}
