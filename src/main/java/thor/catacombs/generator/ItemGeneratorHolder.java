package thor.catacombs.generator;

import org.jetbrains.annotations.Nullable;
import thor.catacombs.generator.items.Item;
import thor.catacombs.info.block.FeelType;

public interface ItemGeneratorHolder {
    @Nullable RandomGenerator<Item> getGenerator(FeelType type);
}
