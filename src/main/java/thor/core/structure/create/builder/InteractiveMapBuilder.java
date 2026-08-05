package thor.core.structure.create.builder;

import lombok.Data;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableLocation;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableWorldBox;
import thor.core.port.mapping.dto.map.InteractiveGameMap;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.AddInfoStructureVisitor;
import thor.core.structure.Structure;
import thor.core.structure.StructureVisitor;
import thor.core.structure.create.InteractiveGameMapWithBoxes;
import thor.core.structure.manager.*;
import thor.core.structure.manager.config.PlacePartManager;

import java.util.ArrayList;
import java.util.List;

public class InteractiveMapBuilder implements BoxStage, InteractivePartStage, LocationStage, MapBuilder, PlaceStage, PlayerTeleportatorStage, WorldAccessorStage, StructuresStage {
    public static WorldAccessorStage builder() {
        return new InteractiveMapBuilder();
    }

    private final List<SignalPartManager> signalManagers = new ArrayList<>();
    private final List<PlacePartManager> sharedPlaceManagers = new ArrayList<>();
    private final List<StructureVisitor> sharedVisitors = new ArrayList<>();
    private final List<StructurePartsManager> partsManagers = new ArrayList<>();

    private ArenaTeleportator arenaTeleportator;
    private PlayerTeleportator playerTeleportator;
    private PortalHandler portalHandler;
    private WorldAccessor accessor;

    private final List<DimensionInfo> dimensions = new ArrayList<>();

    private InteractiveMapBuilder() {}

    @Override
    public StructuresStage withBox(ImmutableBox box) {
        dimensions.getLast().setBox(box);
        return this;
    }

    @Override
    public InteractivePartStage withSignalManager(SignalPartManager signalManager) {
        this.signalManagers.add(signalManager);
        return this;
    }

    @Override
    public InteractivePartStage withArenaTeleportator(ArenaTeleportator arenaTeleportator) {
        this.arenaTeleportator = arenaTeleportator;
        return this;
    }

    @Override
    public InteractivePartStage withPortalHandler(PortalHandler portalHandler) {
        this.portalHandler = portalHandler;
        return this;
    }

    @Override
    public InteractivePartStage withPlace(PlacePartManager partManager) {
        this.sharedPlaceManagers.add(partManager);
        return this;
    }

    @Override
    public InteractivePartStage withStructurePartManager(StructurePartsManager partManager) {
        this.partsManagers.add(partManager);
        return this;
    }

    @Override
    public InteractivePartStage withSharedVisitor(StructureVisitor visitor) {
        sharedVisitors.add(visitor);
        return this;
    }

    @Override
    public MapBuilder withVisitor(StructureVisitor visitor) {
        dimensions.getLast().getVisitors().add(visitor);
        return this;
    }

    @Override
    public InteractiveGameMapWithBoxes build() {
        List<ImmutableWorldBox> boxes = dimensions.stream()
                .map(dimensionInfo -> new ImmutableWorldBox(dimensionInfo.box, dimensionInfo.location.worldName()))
                .toList();
        AddInfoStructureVisitor infoVisitor = new AddInfoStructureVisitor(partsManagers);
        for (DimensionInfo dimensionInfo: dimensions) {
            infoVisitor.setLocation(dimensionInfo.getLocation());
            dimensionInfo.applyVisitors(List.of(infoVisitor));
            dimensionInfo.applyVisitors(sharedVisitors);
            dimensionInfo.create();
        }
        sharedPlaceManagers.forEach(placePartManager -> placePartManager.place(accessor));
        return new InteractiveGameMapWithBoxes(
                new InteractiveGameMap(
                        signalManagers,
                        playerTeleportator,
                        arenaTeleportator,
                        portalHandler
                ),
                boxes
        );
    }

    @Override
    public LocationStage addDimension() {
        dimensions.add(new DimensionInfo());
        return this;
    }

    @Override
    public PlaceStage withLocation(ImmutableLocation location) {
        dimensions.getLast().setLocation(location);
        return this;
    }

    @Override
    public BoxStage withPlaceManager(PlacePartManager placeManager) {
        dimensions.getLast().setPlacePartManager(placeManager);
        return this;
    }

    @Override
    public InteractivePartStage withPlayerTeleportator(PlayerTeleportator playerTeleportator) {
        this.playerTeleportator = playerTeleportator;
        return this;
    }

    @Override
    public PlayerTeleportatorStage withWorldAccessor(WorldAccessor accessor) {
        this.accessor = accessor;
        return this;
    }

    @Override
    public MapBuilder withStructures(List<Structure> structures) {
        dimensions.getLast().setStructures(structures);
        return this;
    }

    @Data
    private class DimensionInfo {
        private ImmutableLocation location;

        private ImmutableBox box;

        private PlacePartManager placePartManager;

        private List<StructureVisitor> visitors = new ArrayList<>();

        private List<Structure> structures;

        public void create() {
            for (StructureVisitor visitor: visitors) {
                structures.forEach(structure -> structure.accept(visitor));
            }
            placePartManager.place(accessor);
        }

        public void applyVisitors(List<StructureVisitor> visitors) {
            for (StructureVisitor visitor: visitors) {
                structures.forEach(structure -> structure.accept(visitor));
            }
        }
    }
}
