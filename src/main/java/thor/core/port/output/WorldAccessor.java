package thor.core.port.output;

import org.bukkit.Material;
import org.bukkit.block.Block;
import thor.core.port.input.LocationDto;
import thor.core.structure.chest.Book;
import thor.core.structure.chest.Item;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.List;

public interface WorldAccessor {
    Block getBlockAt(BlockPosition position);
    void setMapPosition(LocationDto locationDto);
    void placeRoom(String textId, BlockPosition position, boolean rotated);
    void placeTunnel(String textId, BlockPosition position, boolean rotated, int idx);
    void placeChest(BlockPosition position, List<Item> items, List<Book> books, Material material);
    void fill(int x0, int y0, int z0, int x, int y, int z, Material material);
}
