package thor.core.util;

import thor.core.info.Weightable;

public interface RandomGenerator<T extends Weightable> {
    T getRandom(int quality);
    default T getRandom() {
        return getRandom(1);
    }
}
