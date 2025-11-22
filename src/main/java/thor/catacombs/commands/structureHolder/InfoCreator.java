package thor.catacombs.commands.structureHolder;

import org.bukkit.Location;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.structure.interfaces.Nameable;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;

public interface InfoCreator<T extends Nameable> {
    T create(ImmutableBox box, AttributeRegistry registry);
}
