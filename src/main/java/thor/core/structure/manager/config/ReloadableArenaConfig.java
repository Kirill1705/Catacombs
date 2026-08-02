package thor.core.structure.manager.config;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.structure.Structure;
import ru.vikhrenko.serverUtils.reload.YamlAbstractReloadable;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import ru.vikhrenko.serverUtils.utils.dataStructures.Points;
import thor.core.exception.DomainValidationException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReloadableArenaConfig extends YamlAbstractReloadable<ReloadableArenaConfig.Config> implements ArenaConfig {
    private final Path dataPath;

    private Point size;

    public ReloadableArenaConfig(Path dataPath) {
        super(new Config(), Config.class);
        this.dataPath = dataPath;
    }

    @Override
    public Point getButtonPosition() {
        return getOptions().getButton();
    }

    @Override
    public String getArenaPath() {
        return dataPath.resolve(Paths.get(getOptions().arena)).toString();
    }

    @Override
    public Point getSize() {
        return size;
    }

    @Override
    public void validate() {
        try {
            Structure structure = Bukkit.getStructureManager().loadStructure(Paths.get(getArenaPath()).toFile());
            size = Points.fromVector(structure.getSize());
            if (!getButtonPosition().moreOrEquals(new Point(0, 0, 0))) {
                throw new DomainValidationException(getButtonPosition());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    public static class Config {
        String arena = "arena.nbt";

        Point button;
    }
}
