package thor.core.structure.manager;

import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.part.ChestInfo;

import java.util.Collection;

public interface StructurePartsManager<T> {
    void addParts(Collection<T> infoCollection, Converter converter);
}
