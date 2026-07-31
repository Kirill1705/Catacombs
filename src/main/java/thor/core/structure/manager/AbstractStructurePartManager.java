package thor.core.structure.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.structure.AbstractStructurePart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractStructurePartManager<T, U> implements StructurePartsManager<T> {
    @Getter
    private final List<U> parts;

    public AbstractStructurePartManager() {
        parts = new ArrayList<>();
    }

    @Override
    public void addParts(Collection<T> infoCollection, Converter converter) {
        infoCollection.forEach(info -> parts.add(create(info, converter)));
    }

    protected abstract U create(T info, Converter converter);
}
