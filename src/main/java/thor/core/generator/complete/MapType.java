package thor.core.generator.complete;

public enum MapType {
    MAIN,
    WATER;

    public String getWorldName(String mainWorld) {
        return switch (this) {
            case MAIN -> mainWorld;
            case WATER -> mainWorld + "_water";
        };
    }
}
