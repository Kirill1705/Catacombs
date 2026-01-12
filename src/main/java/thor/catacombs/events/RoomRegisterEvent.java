package thor.catacombs.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import thor.catacombs.events.creators.PartTunnelCreator;
import thor.catacombs.events.creators.RoomCreator;
import thor.catacombs.generator.structures.Room;
import thor.catacombs.generator.structures.TunnelPart;
import thor.catacombs.info.structure.interfaces.PartTunnelInfo;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.usefulUtils.utils.dataStructures.BlockPosition;

import java.util.HashMap;
import java.util.Map;

public class RoomRegisterEvent extends Event implements PartTunnelCreator, RoomCreator {
    private final RoomCreator roomCreator;
    private final PartTunnelCreator partTunnelCreator;

    private final Map<String, RoomCreator> roomCreatorMap = new HashMap<>();
    private final Map<String, PartTunnelCreator> tunnelCreatorMap = new HashMap<>();

    public RoomRegisterEvent(RoomCreator roomCreator, PartTunnelCreator partTunnelCreator) {
        this.roomCreator = roomCreator;
        this.partTunnelCreator = partTunnelCreator;
        Bukkit.getPluginManager().callEvent(this);
    }

    public void addRoomCreator(String name, RoomCreator creator) {
        roomCreatorMap.put(name, creator);
    }

    public void addTunnelCreator(String name, PartTunnelCreator creator) {
        tunnelCreatorMap.put(name, creator);
    }

    @Override
    public TunnelPart create(PartTunnelInfo info, BlockPosition position) {
        String name = info.getName();
        if (tunnelCreatorMap.containsKey(name)) {
            return tunnelCreatorMap.get(name).create(info, position);
        }
        return partTunnelCreator.create(info, position);
    }

    @Override
    public Room create(RoomInfo info, BlockPosition position) {
        String name = info.getName();
        if (roomCreatorMap.containsKey(name)) {
            return roomCreatorMap.get(name).create(info, position);
        }
        return roomCreator.create(info, position);
    }

    private static final HandlerList HANDLERS = new HandlerList();
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
