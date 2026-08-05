package thor.core.port.output;

import org.bukkit.Material;
import org.bukkit.block.Block;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import thor.core.structure.chest.Book;
import thor.core.structure.chest.Item;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;
import java.util.UUID;

public interface WorldAccessor {
    Block getBlockAt(Point position, String worldName);
    void placeChest(Point position, List<Item> items, List<Book> books, Material material, String worldName);
    void fill(int x0, int y0, int z0, int x, int y, int z, Material material, String worldName);
    void killEntities(ImmutableBox box, String worldName);
    void placeStructure(String path, Point position, boolean rotated, String worldName);

    void teleportEntity(UUID playerId, Point position, Point direction, String worldName);
    boolean teleportEntityInRandomPlaceInBox(ImmutableBox box, UUID entityId, String worldName);
    void applyEffect(UUID entityId, String effect, int amplifier, int duration);
}
