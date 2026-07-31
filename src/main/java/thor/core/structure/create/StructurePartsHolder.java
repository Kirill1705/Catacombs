package thor.core.structure.create;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thor.core.generator.tunnel.convert.Converter;
import thor.core.info.RoomInfo;
import thor.core.port.output.WorldAccessor;
import thor.core.structure.chest.ItemCreator;
import thor.core.structure.manager.ChestManager;
import thor.core.structure.manager.PlayerSpawnManager;
import thor.core.structure.manager.TeleportManager;

import java.util.List;

public class StructurePartsHolder {
    @Getter
    private final ChestManager chestManager;
    @Getter
    private final PlayerSpawnManager playerSpawnManager;
    @Getter
    private final TeleportManager teleportManager;

    public StructurePartsHolder(ItemCreator itemCreator) {
        this.chestManager = new ChestManager(itemCreator);
        this.teleportManager = new TeleportManager();
        this.playerSpawnManager = new PlayerSpawnManager();
    }

    public void createFromRoomInfo(Converter converter, RoomInfo roomInfo) {
        chestManager.addParts(roomInfo.getChests(), converter);
        playerSpawnManager.addParts(roomInfo.getPlayerSpawnPlaces(), converter);
        teleportManager.addParts(roomInfo.getTeleport() != null ? List.of(roomInfo.getTeleport()) : List.of(), converter);
    }

    public void place(WorldAccessor accessor) {
        chestManager.place(accessor);
    }
}
