package thor.catacombs.info.block;

import org.bukkit.configuration.file.YamlConfiguration;
import thor.catacombs.info.attributes.AttributeHolderType;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.attributes.AttributeType;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

public class MainInfo extends BlockInfo {
    public static final AttributeHolderType TYPE = AttributeHolderType.MAIN;
    private String name;
    private int weight;
    public String getName() {
        return name;
    }
    public int getWeight() {
        return weight;
    }
    void applyMain(YamlConfiguration configuration, AttributeRegistry registry) {
        weight = registry.getAttribute(TYPE, AttributeType.WEIGHT, configuration);
        name = registry.getAttribute(TYPE, AttributeType.NAME, configuration);
    }
    public MainInfo(BlockPosition position, YamlConfiguration configuration, AttributeRegistry registry) {
        super(position);
        applyMain(configuration, registry);
    }
}
