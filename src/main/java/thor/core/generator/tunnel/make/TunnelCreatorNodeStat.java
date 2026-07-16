package thor.core.generator.tunnel.make;

public enum TunnelCreatorNodeStat {
    ROTATION_ENABLED,
    VERTICAL,
    ROTATION_DISABLED;
    public TunnelCreatorNodeStat nextStatus() {
        return switch (this) {
            case ROTATION_ENABLED -> VERTICAL;
            case VERTICAL -> ROTATION_DISABLED;
            case ROTATION_DISABLED -> throw new RuntimeException();
        };
    }
}
