package thor.catacombs.info.attributes;

public enum AttributeHolderType {
    MAIN,
    CHEST,
    EXIT,
    PLAYER_SPAWN_PLACE;
    public String getName() {
        return this.toString().toLowerCase();
    }
}
