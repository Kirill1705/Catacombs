package thor.core.structure.create.builder;

import thor.core.port.mapping.dto.map.InteractiveGameMap;
import thor.core.structure.StructureVisitor;
import thor.core.structure.create.InteractiveGameMapWithBoxes;

public interface MapBuilder {
    MapBuilder withVisitor(StructureVisitor visitor);

    InteractiveGameMapWithBoxes build();

    LocationStage addDimension();
}
