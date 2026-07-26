package thor.core.port.output;

import java.util.List;

public interface StructureManager {
    void addRoomStructure(String structurePath, String textId);
    void addTunnelStructure(List<String> structurePaths, String textId);

    String getRoomStructurePath(String textId);
    List<String> getTunnelPartPaths(String textId, int size);
    String getTunnelPartPath(String textId, int idx);
}
