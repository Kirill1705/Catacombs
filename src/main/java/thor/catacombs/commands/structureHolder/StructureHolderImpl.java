package thor.catacombs.commands.structureHolder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.structure.Structure;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.structure.interfaces.Nameable;
import thor.usefulUtils.utils.OtherUtils;
import thor.usefulUtils.utils.StructureUtils;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.BlockPosition;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;
import thor.usefulUtils.utils.dataStructures.Point;

import javax.annotation.Nullable;
import java.util.*;

public abstract class StructureHolderImpl<T extends Nameable> implements StructureHolder<T>{
    private final String namespace;
    private final BlockLocation location;
    private final AttributeRegistry registry;
    private final InfoCreator<T> creator;
    private final List<T> structures = new ArrayList<>();
    public StructureHolderImpl(String namespace, World world, AttributeRegistry registry, InfoCreator<T> creator) {
        this.namespace = namespace;
        this.registry = registry;
        this.location = new BlockLocation(100, 100, 100, world);
        this.creator = creator;
        var map = Bukkit.getStructureManager().getStructures();
        if (map.isEmpty()) {
            System.out.println("no structures registered!");
        }
        List<Structure> structures = map.entrySet().stream()
                .filter(entry -> entry.getKey().namespace().equalsIgnoreCase(namespace))
                .map(Map.Entry::getValue).toList();
        for (Structure structure: structures) {
            addStructure(structure);
        }
    }
    protected void fillAir(ImmutableBox box) {
        box = new ImmutableBox(box.begin().subtract(new Point(2, 2, 2)), box.end().add(new Point(2, 2, 2)));
        OtherUtils.fill(box, Material.AIR);
        Collection<Entity> entities = box.begin().world().getNearbyEntities(box.toBoundingBox());
        for (Entity entity: entities) {
            if (entity.getType()!= EntityType.PLAYER) {
                entity.remove();
            }
        }
    }
    protected @Nullable T loadStructure(Structure structure, StructurePlacer placer, boolean replaceByName) {
        ImmutableBox box = new ImmutableBox(location, location.add(placer.getSize(structure)).subtract(new Point(1, 1,1)));
        fillAir(box);
        placer.place(structure, location);
        T info;
        try {
            info = creator.create(box, registry);
            if (replaceByName) {
                deleteStructure(info.getName());
            }
            StructureUtils.save(structure, new NamespacedKey(namespace, info.getName()));
            structures.add(info);
            fillAir(box);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean placeStructure(String name, BlockLocation location) {
        NamespacedKey key = new NamespacedKey(namespace, name);
        Structure structure = StructureUtils.load(key);
        if (structure==null) {
            return false;
        }
        StructureUtils.place(structure, location.toLocation());
        return true;
    }
    @Override
    public boolean deleteStructure(String name) {
        NamespacedKey key = new NamespacedKey(namespace, name);
        StructureUtils.deleteStructure(key);
        Optional<T> optional = structures.stream().filter(info -> info.getName().equals(name)).findFirst();
        if (optional.isEmpty()) {
            return false;
        }
        structures.remove(optional.get());
        return true;
    }
    @Override
    public List<T> getStructures() {
        return Collections.unmodifiableList(structures);
    }

    protected interface StructurePlacer {
        void place(Structure structure, BlockLocation location);
        BlockPosition getSize(Structure structure);
    }

    protected static class UsualStructurePlacer implements StructurePlacer {
        @Override
        public void place(Structure structure, BlockLocation location) {
            StructureUtils.place(structure, location.toLocation());
        }
        @Override
        public BlockPosition getSize(Structure structure) {
            return new Point(structure.getSize());
        }

    }
}
