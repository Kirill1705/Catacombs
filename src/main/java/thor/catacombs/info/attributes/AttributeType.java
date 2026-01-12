package thor.catacombs.info.attributes;

public enum AttributeType {
    NAME,
    WEIGHT,
    SIZE,
    QUALITY,
    PROBABILITY,
    FILL_TYPE,
    MATERIAL,
    PRIORITY;
    public String getName() {
        return this.toString().toLowerCase();
    }
}
