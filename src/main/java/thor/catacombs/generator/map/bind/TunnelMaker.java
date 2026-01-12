package thor.catacombs.generator.map.bind;

import thor.catacombs.generator.Exit;
import thor.catacombs.generator.map.ImmutableGraph;
import thor.catacombs.generator.structures.TunnelPart;

import javax.annotation.Nullable;
import java.util.List;

public interface TunnelMaker {
    @Nullable
    List<TunnelPart> tryToMakeTunnel(Exit first, Exit second, ImmutableGraph graph);
}
