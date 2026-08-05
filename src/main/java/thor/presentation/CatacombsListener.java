package thor.presentation;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import ru.vikhrenko.serverUtils.utils.dataStructures.Points;
import thor.core.info.SignalType;
import thor.core.port.input.MapEngineService;

import java.util.UUID;

@RequiredArgsConstructor
public class CatacombsListener implements Listener {
    private final MapEngineService service;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.PHYSICAL) {
            processInteraction(event.getPlayer(), event.getClickedBlock());
        }
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        processInteraction(event.getEntity(), event.getBlock());
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        handlePortalEvent(event.getPlayer(), event.getFrom(), event.getTo(), event);
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        handlePortalEvent(event.getEntity(), event.getFrom(), event.getTo(), event);
    }

    private void processInteraction(Entity entity, Block block) {
        if (block == null || entity == null) return;

        SignalType type = getActivatorType(block);
        if (type == SignalType.UNKNOWN) return;

        if (block.getBlockData() instanceof AnaloguePowerable powerable) {
            if (powerable.getPower() != 0) {
                return;
            }
        }
        Location blockLocation = block.getLocation();
        service.onPressedSomething(entity.getUniqueId(), Points.fromLocation(blockLocation), blockLocation.getWorld().getName(), type.name());
    }

    private SignalType getActivatorType(Block block) {
        String typeName = block.getType().name();
        if (block.getBlockData() instanceof Switch) {
            return SignalType.BUTTON;
        }
        if (block.getBlockData() instanceof AnaloguePowerable || block.getBlockData() instanceof Powerable && typeName.contains("PRESSURE_PLATE")) {
            return SignalType.PLATE;
        }

        return SignalType.UNKNOWN;
    }

    private void handlePortalEvent(Entity entity, Location from, Location to, Cancellable event) {
        if (to == null || to.getWorld() == null || from.getWorld() == null) {
            return;
        }

        UUID entityId = entity.getUniqueId();
        String sourceWorld = from.getWorld().getName();
        String destWorld = to.getWorld().getName();
        Point position = Points.fromLocation(from);
        if (service.onTeleportingToAnotherWorld(entityId, position, sourceWorld, destWorld)) {
            event.setCancelled(true);
        }
    }
}
