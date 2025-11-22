package thor.catacombs.generator.map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import thor.usefulUtils.utils.OtherUtils;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;

public class GeneratorConfigurator {
    private Point size = new Point(256, 64, 256);
    private double density = 5e-5;
    private int roomsCap = Integer.MAX_VALUE;

    public BlockPosition getSize() {
        return size;
    }

    public double getDensity() {
        return density;
    }

    public int getRoomsCap() {
        return roomsCap;
    }

    public GeneratorConfigurator(FileConfiguration configuration) {
        ConfigurationSection mapSection = configuration.getConfigurationSection("map");
        if (mapSection == null) return;
        OtherUtils.configureVariable("rooms-density", s -> density = mapSection.getDouble(s), mapSection);
        OtherUtils.configureVariable("size", s -> {
            List<Integer> list = mapSection.getIntegerList(s);
            size = new Point(list.get(0), list.get(1), list.get(2));
        }, mapSection);
        OtherUtils.configureVariable("rooms-capacity", s -> roomsCap = mapSection.getInt(s), mapSection);
    }
}
