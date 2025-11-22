package thor.catacombs.generator.items;

import thor.catacombs.generator.Weightable;
import thor.customFeatures.items.ExtendedItemStack;

public interface Item extends Weightable {
    ExtendedItemStack getItemStack();
    int getQuantity();
}
