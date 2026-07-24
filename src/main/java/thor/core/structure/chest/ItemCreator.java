package thor.core.structure.chest;

import thor.core.exception.EmptyListForGeneratorException;
import thor.core.exception.MissingItemsForFillTypeException;
import thor.core.info.ItemInfo;
import thor.core.info.part.FillType;
import thor.core.util.RandomGenerator;
import thor.core.util.RandomGeneratorImpl;

import java.util.*;

public class ItemCreator {
    private final Map<FillType, RandomGenerator<ItemInfo>> generators = new HashMap<>();

    public ItemCreator(Collection<ItemInfo> items) {
        for (FillType type: FillType.values()) {
            List<ItemInfo> typeItems = items.stream()
                    .filter(itemInfo -> itemInfo.getFillType() == type)
                    .toList();
            try {
                generators.put(type, new RandomGeneratorImpl<>(typeItems));
            }
            catch (EmptyListForGeneratorException e) {
                throw new MissingItemsForFillTypeException(type.name());
            }
        }
    }

    public List<ItemInfo> getItems(FillType type, int quantity, int quality) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity cant be less then zero");
        }
        List<ItemInfo> result = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            result.add(generators.get(type).getRandom(quality));
        }
        return result;
    }
}
