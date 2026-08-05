package thor.core.structure.create.builder;

import thor.core.structure.Structure;

import java.util.List;

public interface StructuresStage {
    MapBuilder withStructures(List<Structure> structures);
}
