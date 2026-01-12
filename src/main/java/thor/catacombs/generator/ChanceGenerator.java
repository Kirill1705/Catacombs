package thor.catacombs.generator;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ChanceGenerator<T extends Weightable> implements RandomGenerator<T> {
    private final TreeMap<Long, T> chanceMap = new TreeMap<>();
    private long totalWeight = 0;

    public ChanceGenerator(List<T> info) {
        Preconditions.checkArgument(!info.isEmpty(), "List should contains at least 1 object!");
        info = new ArrayList<>(info);
        info.sort((o1, o2) -> Integer.compare(o2.getWeight(), o1.getWeight()));
        for (T item : info) {
            totalWeight += item.getWeight();
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
