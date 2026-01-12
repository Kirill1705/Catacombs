package thor.catacombs.info.structure.interfaces;

import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.generator.BlockInfoGenerator;

public interface ExtendedStructureInfo extends StructureInfo {
    BlockInfoGenerator getGenerator();
    AttributeRegistry getRegistry();
}
