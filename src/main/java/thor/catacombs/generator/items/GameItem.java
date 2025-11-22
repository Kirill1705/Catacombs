package thor.catacombs.generator.items;

import thor.usefulUtils.dataStructures.Pair;
import thor.customFeatures.items.ExtendedItemStack;

public class GameItem implements Item {
    private final int weight;

    public ExtendedItemStack getItemStack() {
        return itemStack;
    }

    public int getQuantity() {
        return (int) (quantity.first+Math.random()*(quantity.second-quantity.first+1));
    }

    private final ExtendedItemStack itemStack;
    private final Pair<Integer, Integer> quantity;
    public GameItem(int minCount, int maxCount, String name, int weight) {
        this(minCount, maxCount, new ExtendedItemStack(name), weight);
    }
    protected GameItem(int minCount, int maxCount, ExtendedItemStack itemStack, int weight) {
        this.weight = weight;
        quantity = Pair.of(minCount, maxCount);
        this.itemStack = itemStack;
    }
    @Override
    public int getWeight() {
        return weight;
    }
}
