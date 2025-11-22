package thor.catacombs.generator;

public interface RandomGenerator<T extends Weightable> {
    T getRandom(int quality);
    default T getRandom() {
        return getRandom(1);
    }
}
