package thor.catacombs.generator;

import thor.catacombs.generator.structures.TunnelPart;

import java.util.Collections;
import java.util.List;

public record Tunnel(List<TunnelPart> data) {
    @Override
    public List<TunnelPart> data() {
        return Collections.unmodifiableList(data);
    }
}
