package thor.catacombs.generator;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;
import thor.catacombs.generator.items.EnchantedBook;
import thor.catacombs.generator.items.GameItem;
import thor.catacombs.generator.items.Item;
import thor.catacombs.info.block.FeelType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemInfoGenerator implements ItemGeneratorHolder{
    private final Map<FeelType, RandomGenerator<Item>> generators = new HashMap<>();
    private void addItems(ConfigurationSection config, List<Item> items) {
        if (config==null||!config.contains("items")) return;
        List<Map<?, ?>> objects = config.getMapList("items");
        for (Map<?, ?> map: objects) {
            try {
                int min = 1;
                int max = 64;
                if (!map.containsKey("min") && !map.containsKey("max")) {
                    max = 1;
                }
                if (map.containsKey("min")) {
                    min = (int) map.get("min");
                }
                if (map.containsKey("max")) {
                    max = (int) map.get("max");
                }
                items.add(new GameItem(min, max, (String) map.get("id"), (int) map.get("weight")));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void addBooks(ConfigurationSection config, List<Item> items) {
        if (config==null||!config.contains("books")) return;
        List<Map<?, ?>> objects = config.getMapList("books");
        for (Map<?, ?> map: objects) {
            try {
                items.add(new EnchantedBook((String)map.get("id"), (int)map.get("weight"), (int)map.get("level")));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private RandomGenerator<Item> setupItemGenerator(String fileName, File folder) {
        File file = new File(folder, fileName);
        YamlConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
        List<Item> items = new ArrayList<>();
        addItems(itemConfig, items);
        addBooks(itemConfig, items);
        return new ChanceGenerator<>(items);
    }
    public ItemInfoGenerator(File folder, Map<FeelType, String> fileNames) {
        for (var entry: fileNames.entrySet()) {
            generators.put(entry.getKey(), setupItemGenerator(entry.getValue(), folder));
        }
    }
    @Override
    public @Nullable RandomGenerator<Item> getGenerator(FeelType type) {
        return generators.get(type);
    }
}
