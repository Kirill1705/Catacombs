package thor.core.structure.create.builder;

import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;

public interface BoxStage {
    StructuresStage withBox(ImmutableBox box);
}
