package thor.infrastructure;

import lombok.AllArgsConstructor;
import thor.core.port.output.StructureManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class StructureManagerImpl implements StructureManager {
    private final Path structuresPath;

    @Override
    public void addRoomStructure(String structurePath, String textId) {
        try {
            Files.copy(Paths.get(structurePath), structuresPath.resolve(textId + ".nbt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTunnelStructure(List<String> structurePaths, String textId) {
        File directory = structuresPath.resolve(textId).toFile();
        if (directory.exists() && directory.isFile()) {
            throw new IllegalStateException("Cant mkdir. File with path " + structuresPath.resolve(textId) + " exists");
        }
        if (!directory.exists()) {
            directory.mkdir();
        }
        for (int i = 0; i < structurePaths.size(); i++) {
            addRoomStructure(structurePaths.get(i), textId + "/" + i);
        }
    }

    @Override
    public String getRoomStructurePath(String textId) {
        return structuresPath.resolve(textId + ".nbt").toString();
    }

    @Override
    public List<String> getTunnelPartPaths(String textId, int size) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(getRoomStructurePath(textId + "/" + i));
        }
        return result;
    }

    @Override
    public String getTunnelPartPath(String textId, int idx) {
        return getRoomStructurePath(textId + "/" + idx);
    }
}
