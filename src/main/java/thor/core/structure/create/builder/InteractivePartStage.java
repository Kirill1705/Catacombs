package thor.core.structure.create.builder;

import thor.core.structure.StructureVisitor;
import thor.core.structure.manager.ArenaTeleportator;
import thor.core.structure.manager.PortalHandler;
import thor.core.structure.manager.SignalPartManager;
import thor.core.structure.manager.StructurePartsManager;
import thor.core.structure.manager.config.PlacePartManager;

public interface InteractivePartStage {
    InteractivePartStage withSignalManager(SignalPartManager signalManager);

    InteractivePartStage withArenaTeleportator(ArenaTeleportator arenaTeleportator);

    InteractivePartStage withPortalHandler(PortalHandler portalHandler);

    InteractivePartStage withPlace(PlacePartManager partManager);

    InteractivePartStage withStructurePartManager(StructurePartsManager partManager);

    InteractivePartStage withSharedVisitor(StructureVisitor visitor);

    LocationStage addDimension();
}
