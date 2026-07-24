package thor.core.util;

import thor.core.exception.EmptyListForGeneratorException;
import thor.core.info.Weightable;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class RandomGeneratorImpl<T extends Weightable> implements RandomGenerator<T> {
    private final TreeMap<Long, T> chanceMap = new TreeMap<>();
    private long totalWeight = 0;

    public RandomGeneratorImpl(List<T> info) {
        if (info.isEmpty()) {
            throw new EmptyListForGeneratorException();
        };
        info = new ArrayList<>(info);
        info.sort((o1, o2) -> Integer.compare(o2.getWeight().value(), o1.getWeight().value()));
        for (T item : info) {
            totalWeight += item.getWeight().value();
            chanceMap.put(totalWeight, item);
        }
    }

    @Override
    public T getRandom(int quality) {
        double pow = 1+(quality-1)/10D;
        long rand = (long) (Math.random() * Math.pow(totalWeight, pow));
        T result = chanceMap.higherEntry((long) Math.pow(rand, 1/pow)).getValue();
        if (result==null) getRandom(quality);
        return result;
    }
}
