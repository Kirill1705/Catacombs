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
import org.bukkit.structure.Structure;
import thor.core.exception.InvalidEnchantException;
import thor.core.exception.InvalidMaterialException;
import thor.core.port.mapping.LocationDto;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.Book;
import thor.core.structure.chest.Item;
import thor.customFeatures.items.ExtendedItemStack;
import thor.usefulUtils.utils.OtherUtils;
import thor.usefulUtils.utils.dataStructures.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class WorldAccessorImpl implements WorldAccessor {
    private final BlockLocation location;

    private final Map<String, Structure> cache = new HashMap<>();

    @Override
    public Block getBlockAt(Point position) {
        return location.world().getBlockAt(createLocation(position));
    }

    @Override
    public void placeChest(Point position, List<Item> items, List<Book> books, Material material) {
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
        throw new InvalidMaterialException(material.name(), "Not container");
    }

    @Override
    public void fill(int x0, int y0, int z0, int x, int y, int z, Material material) {
        Point corner1 = convertPosition(new Point(x0, y0, z0));
        Point corner2 = convertPosition(new Point(x, y, z));
        OtherUtils.fill(Boxes.fromCorners(corner1, corner2), material, location.world(), false);
    }

    @Override
    public void placeStructure(String path, Point position, boolean rotated) {
        Structure structure;
        if (cache.containsKey(path)) {
            structure = cache.get(path);
        }
        else {
            File nbt = new File(path + ".nbt");
            try {
                structure = Bukkit.getServer().getStructureManager().loadStructure(nbt);
                cache.put(path, structure);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        structure.place(createLocation(position), true, rotated ? StructureRotation.COUNTERCLOCKWISE_90 : StructureRotation.NONE, Mirror.NONE, 0, 1, new Random());
    }

    @Override
    public LocationDto getPlayerLocation(UUID playerID) {
        Location loc = Bukkit.getPlayer(playerID).getLocation();
        return new LocationDto(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
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
            if (enchantment==null) throw new InvalidEnchantException(book.enchId());
            meta.addStoredEnchant(enchantment, book.level(), true);
            itemStack.setItemMeta(meta);
            itemStacks.add(itemStack);
        }
        return itemStacks;
    }

    private Location createLocation(Point position) {
        return convertPosition(position).toLocation(location.world());
    }

    private Point convertPosition(Point position) {
        return BlockLocations.toPoint(location).add(position);
    }
}
