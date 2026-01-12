package thor.catacombs.generator.structures;

import thor.catacombs.generator.Exit;
import thor.catacombs.generator.structures.utils.StructureLocation;
import thor.catacombs.info.block.ExitInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExitLoader {
    private final List<Exit> exits = new ArrayList<>();

    public ExitLoader(Collection<ExitInfo> exitsInfo, StructureLocation position) {
        for (ExitInfo exitInfo: exitsInfo) {
            exits.add(new Exit(exitInfo, position));
        }
    }
    public List<Exit> getExits() {
        return Collections.unmodifiableList(exits);
    }
}
