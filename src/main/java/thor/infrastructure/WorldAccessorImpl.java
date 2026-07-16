package thor.infrastructure;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;
import thor.core.port.input.LocationDto;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.Book;
import thor.core.structure.chest.Item;
import thor.customFeatures.items.ExtendedItemStack;
import thor.usefulUtils.utils.OtherUtils;
import thor.usefulUtils.utils.StructureUtils;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;
import thor.usefulUtils.utils.dataStructures.Point;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class WorldAccessorImpl implements WorldAccessor {
    private final Plugin plugin;
    private final Path structuresPath;

    private BlockLocation location;

    @Override
    public Block getBlockAt(BlockPosition position) {
        return location.world().getBlockAt(createLocation(position));
    }

    @Override
    public void setMapPosition(LocationDto locationDto) {
        this.location = new BlockLocation(locationDto.x(), locationDto.y(), locationDto.z(), Bukkit.getWorld(locationDto.worldName()));
    }

    @Override
    public void placeRoom(String textId, BlockPosition position, boolean rotated) {
        try {
            File nbt = new File(structuresPath.toFile(), textId + ".nbt");
            Structure structure = plugin.getServer().getStructureManager().loadStructure(nbt);
            structure.place(createLocation(position), true, rotated ? StructureRotation.COUNTERCLOCKWISE_90 : StructureRotation.NONE, Mirror.NONE, 0, 1, new Random());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void placeTunnel(String textId, BlockPosition position, boolean rotated, int idx) {
        placeRoom(textId + "/" + idx, position, rotated);
    }

    @Override
    public void placeChest(BlockPosition position, List<Item> items, List<Book> books, Material material) {
        Location location = createLocation(position);
        Block block = location.getBlock();
        block.setType(material);
        if (block.getState() instanceof Container container) {
            container.getInventory().clear();
            for (ItemStack itemStack: getItems(items, books)) {
                container.getInventory().addItem(itemStack);
            }
            return;
        }
        throw new RuntimeException(material + " is not container!");
    }

    @Override
    public void fill(int x0, int y0, int z0, int x, int y, int z, Material material) {
        BlockLocation corner1 = new BlockLocation(createLocation(this.location.add(new Point(x0, y0, z0))));
        BlockLocation corner2 = new BlockLocation(createLocation(this.location.add(new Point(x, y, z))));
        OtherUtils.fill(new ImmutableBox(corner1, corner2), material, false);
    }

    private List<ItemStack> getItems(List<Item> items, List<Book> books) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (Item item: items) {
            ExtendedItemStack itemStack = new ExtendedItemStack(item.textId(), item.quantity());
            itemStacks.add(itemStack.toItemStack());
        }
        for (Book book: books) {
            ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
            Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(NamespacedKey.minecraft(book.enchId()));
            if (enchantment==null) throw new RuntimeException("invalid enchantment name "+book.enchId());
            meta.addStoredEnchant(enchantment, book.level(), true);
            itemStack.setItemMeta(meta);
            itemStacks.add(itemStack);
        }
        return itemStacks;
    }

    private Location createLocation(BlockPosition position) {
        return this.location.add(position).toBlockVector().toLocation(location.world());
    }
}
