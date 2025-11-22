package thor.catacombs.commands.structureHolder;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.catacombs.info.structure.interfaces.TunnelInfo;

import java.util.List;

public class StructureLoaderFacade {
    private final StructureHolder<RoomInfo> rooms;
    private final StructureHolder<TunnelInfo> tunnels;

    public StructureLoaderFacade(Plugin plugin, World world, AttributeRegistry registry) {
        rooms = new RoomHolder(plugin, world, registry, new GameRoomInfoCreator());
        tunnels = new TunnelHolder(plugin, world, registry, new GameTunnelInfoCreator());
        StructureLoader loader = new StructureLoader(plugin, rooms, tunnels);
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register("rooms", "Manage catacombs structures!", loader);
        });
    }

    public List<RoomInfo> getRooms() {
        return rooms.getStructures();
    }

    public List<TunnelInfo> getTunnels() {
        return tunnels.getStructures();
    }
}
