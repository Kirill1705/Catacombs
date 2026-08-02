package thor.infrastructure;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.structure.Structure;
import thor.core.exception.InvalidEnchantException;
import thor.core.exception.InvalidMaterialException;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.Book;
import thor.core.structure.chest.Item;
import thor.customFeatures.items.ExtendedItemStack;
import ru.vikhrenko.serverUtils.utils.OtherUtils;
import ru.vikhrenko.serverUtils.utils.dataStructures.*;

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
            File nbt = new File(path);
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
    public void teleportEntity(UUID playerId, Point position, Point direction) {
        Entity entity = Bukkit.getEntity(playerId);
        if (entity == null) return;
        Location location = createLocation(position);
        location = location.toCenterLocation();
        location.setY(location.y() - 0.4);
        if (direction != null) {
            location.setDirection(direction.toBlockVector().normalize());
        }
        entity.teleport(location);
        entity.sendMessage(Component.text("Teleporting successful!").color(NamedTextColor.GREEN));
    }

    @Override
    public boolean teleportEntityInRandomPlaceInBox(ImmutableBox box, UUID entityId) {
        World world = location.world();
        Point position = BlockLocations.toPoint(location).add(box.begin());
        Point size = box.size();
        final int maxAttemptCount = 10;
        for (int i = 0; i < maxAttemptCount; i++) {
            int x = (int) (position.x() + 1 + Math.random()*(size.x() - position.x() - 1));
            int y = (int) (position.y() + 1 + Math.random()*(size.y() - position.y() - 1));
            int z = (int) (position.z() + 1 + Math.random()*(size.z() - position.z() - 1));
            Location location = new Location(world, x, y, z);
            Block block = location.getBlock();
            Block upper = block.getRelative(0, 1, 0);
            if (i == maxAttemptCount - 1) {
                block.setType(Material.AIR);
                upper.setType(Material.AIR);
            }
            if (!block.isSolid() && !upper.isSolid()) {
                Entity entity = Bukkit.getEntity(entityId);
                if (entity == null) return false;
                entity.teleport(location);
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyEffect(UUID entityId, String effect, int amplifier, int duration) {
        if (Bukkit.getEntity(entityId) instanceof LivingEntity entity) {
            NamespacedKey key = NamespacedKey.minecraft(effect);
            PotionEffectType effectType = Registry.POTION_EFFECT_TYPE.get(key);
            entity.addPotionEffect(new PotionEffect(effectType, duration, amplifier));
        }
    }

    @Override
    public String getWorldName() {
        return location.world().getName();
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
