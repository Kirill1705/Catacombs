package thor.catacombs.game.configurators;

import org.bukkit.Material;
import thor.catacombs.info.attributes.AttributeHolderType;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.attributes.AttributeType;
import thor.catacombs.info.attributes.ConfigAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameAttributesConfigurator implements AttributesConfigurator {
    private final AttributeRegistry registry;
    @Override
    public AttributeRegistry getRegistry() {
        return registry;
    }
    public GameAttributesConfigurator() {
        AttributeRegistry.Builder builder = new AttributeRegistry.Builder();
        builder.addAttribute(AttributeHolderType.MAIN, List.of(
                new ConfigAttribute<>(AttributeType.NAME, Collections.emptyList(), _ -> true),
                new ConfigAttribute<>(AttributeType.WEIGHT, List.of("1", "2", "3", "5", "10", "20", "40", "100"), value -> value > 0 && value <= 500, 20)
        ));
        builder.addAttribute(AttributeHolderType.CHEST, List.of(
                new ConfigAttribute<>(AttributeType.QUALITY, List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), value -> value>=1&&value<=10, 5),
                new ConfigAttribute<>(AttributeType.SIZE, List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), value -> value>=1&&value<=10, 5),
                new ConfigAttribute<>(AttributeType.FILL_TYPE, List.of("chest", "barrel"), s -> s.equals("chest")||s.equals("barrel"), "chest"),
                new ConfigAttribute<>(AttributeType.PROBABILITY, List.of("0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1"), value -> value>0&&value<=1, 1D)
        ));
        Collection<String> suggest = new ArrayList<>();
        for (Material material: Material.values()) {
            suggest.add(material.toString().toLowerCase());
        }
        builder.addAttribute(AttributeHolderType.EXIT, List.of(
                new ConfigAttribute<>(AttributeType.MATERIAL, suggest, s -> {
                    try {
                        Material.valueOf(s.toUpperCase());
                        return true;
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                }, "glass")
        ));
        builder.addAttribute(AttributeHolderType.PLAYER_SPAWN_PLACE, List.of());
        registry = builder.build();
    }
}
