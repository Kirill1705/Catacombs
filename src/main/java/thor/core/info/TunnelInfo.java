package thor.core.info;

import lombok.Getter;
import thor.core.exception.DomainValidationException;
import thor.core.info.part.PartTunnelDescription;
import thor.core.info.part.PartTunnelInfo;
import thor.core.info.part.TunnelType;
import thor.core.info.part.Weight;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.ArrayList;
import java.util.List;

public class TunnelInfo extends StructureInfo {
    @Getter
    private final TunnelType type;
    @Getter
    private final List<PartTunnelInfo> parts;
    @Getter
    private final boolean isNeutral;

    public TunnelInfo(Point size, Weight weight, String name, TunnelType type, List<PartTunnelDescription> parts, boolean isNeutral) {
        super(size, weight, name, List.of(), List.of());
        this.isNeutral = isNeutral;
        if (parts.isEmpty()) {
            throw new DomainValidationException(parts);
        }
        this.type = type;
        this.parts = new ArrayList<>();
        for (int i = 0; i < parts.size(); i++) {
            PartTunnelDescription part = parts.get(i);
            this.parts.add(new PartTunnelInfo(name, weight, part.attachmentPoint(), type, part.size(), part.chests(), part.playerSpawnPlaces(), i));
        }
    }

    public PartTunnelInfo getByIdx(int idx) {
        idx = idx % parts.size();
        return parts.get(idx);
    }
}
