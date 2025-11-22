package thor.catacombs.info.structure.interfaces;

import thor.catacombs.info.block.generator.BlockInfoGenerator;
import thor.catacombs.info.attributes.AttributeRegistry;

public interface ExtendedStructureInfo extends StructureInfo {
    BlockInfoGenerator getGenerator();
    AttributeRegistry getRegistry();
}
