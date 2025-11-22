package thor.catacombs.info.block.generator;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.catacombs.info.block.BlockInfo;
import thor.catacombs.info.block.creators.BlockInfoCreator;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;

import java.util.*;

public class BlockInfoGenerator {
    private final Map<String, List<BlockData>> data = new HashMap<>();
    private final List<TextDisplay> displays;
    private final ImmutableBox box;
    public BlockLocation getBegin() {
        return box.begin();
    }
    public ImmutableBox getBox() {
        return box;
    }
    private ArrayList<TextDisplay> getAllMetaInfo(ImmutableBox box) {
        Collection<Entity> entities = box.begin().world().getNearbyEntities(box.toBoundingBox());
        ArrayList<TextDisplay> result = new ArrayList<>();
        for (Entity entity: entities) {
            if (entity instanceof TextDisplay textDisplay) {
                result.add(textDisplay);
            }
        }
        return result;
    }
    public BlockInfoGenerator(ImmutableBox box) {
        this.box = box;
        displays = getAllMetaInfo(box);
        for (TextDisplay display: displays) {
            String text = PlainTextComponentSerializer.plainText().serialize(display.text());
            YamlConfiguration configuration = new YamlConfiguration();
            try {
                configuration.loadFromString(text);
                BlockPosition position = new BlockLocation(display.getLocation()).subtract(box.begin());
                String type = configuration.getString("type");
                if (type == null) throw new RuntimeException("type in TextDisplay in room is required!");
                if (!data.containsKey(type)) {
                    data.put(type, new ArrayList<>());
                }
                data.get(type).add(new BlockData(position, configuration));
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public <T extends BlockInfo> List<T> getBlockInfo(String type, BlockInfoCreator<T> creator) {
        if (!data.containsKey(type))
            return List.of();
        return data.get(type).stream()
                .map(blockData -> creator.create(blockData.vector, blockData.config, box.begin().add(blockData.vector)))
                .toList();
    }
    public void killAllDisplays() {
        for (var display: displays) {
            display.remove();
        }
    }
    private record BlockData(BlockPosition vector, YamlConfiguration config) {}
}
