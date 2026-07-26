package thor.core.port.output;

import org.bukkit.Material;
import org.bukkit.block.Block;
import thor.core.port.mapping.LocationDto;
import thor.core.structure.chest.Book;
import thor.core.structure.chest.Item;
import thor.usefulUtils.utils.dataStructures.Point;

import java.util.List;
import java.util.UUID;

public interface WorldAccessor {
    Block getBlockAt(Point position);
    void placeChest(Point position, List<Item> items, List<Book> books, Material material);
    void fill(int x0, int y0, int z0, int x, int y, int z, Material material);
    void placeStructure(String path, Point position, boolean rotated);
    LocationDto getPlayerLocation(UUID playerID);
}
