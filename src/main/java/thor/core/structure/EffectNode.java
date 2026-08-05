package thor.core.structure;

import lombok.Getter;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.EffectInfo;

public class EffectNode extends AbstractStructurePart {
    @Getter
    private final EffectInfo effectInfo;

    public EffectNode(StructurePartPlaceInfo placeInfo, EffectInfo effectInfo) {
        super(placeInfo, effectInfo.position());
        this.effectInfo = effectInfo;
    }
}
