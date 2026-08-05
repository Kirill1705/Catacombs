package thor.core.structure.create;

import lombok.RequiredArgsConstructor;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.IslandInfo;
import thor.core.structure.Island;
import thor.core.structure.Structure;
import thor.core.util.RandomGenerator;

@RequiredArgsConstructor
public class IslandsCreator implements StructureCreator {
    private final RandomGenerator<IslandInfo> randomGenerator;

    @Override
    public Structure create(Point position) {
        return new Island(Converter.simple(position), randomGenerator.getRandom());
    }
}
