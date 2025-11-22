package thor.catacombs.commands.structureHolder;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.structure.Structure;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import thor.catacombs.commands.MarkupAxable;
import thor.catacombs.info.structure.interfaces.Nameable;
import thor.catacombs.info.structure.interfaces.RoomInfo;
import thor.catacombs.info.structure.interfaces.TunnelInfo;
import thor.usefulUtils.utils.StructureUtils;
import thor.usefulUtils.utils.dataStructures.BlockLocation;
import thor.usefulUtils.utils.dataStructures.ImmutableBox;

import java.util.*;

public class StructureLoader extends MarkupAxable implements BasicCommand {
    private final StructureHolder<RoomInfo> rooms;
    private final StructureHolder<TunnelInfo> tunnels;

    public StructureLoader(Plugin plugin, StructureHolder<RoomInfo> rooms, StructureHolder<TunnelInfo> tunnels) {
        super(plugin);
        this.rooms = rooms;
        this.tunnels = tunnels;
    }

    private boolean tryToLoadStructure(String type, Structure structure) {
        return switch (type) {
            case "tunnel" -> tunnels.addStructure(structure);
            case "room" -> rooms.addStructure(structure);
            default -> false;
        };
    }
    private Collection<String> getAllNames(String type) {
        return switch (type) {
            case "room" -> rooms.getStructures().stream()
                    .map(Nameable::getName)
                    .toList();
            case "tunnel" -> tunnels.getStructures().stream()
                    .map(Nameable::getName)
                    .toList();
            default -> null;
        };
    }
    private void loadStructure(Player player, Structure structure, String type) {
        if (tryToLoadStructure(type, structure)) {
            player.sendMessage(Component.text("Новая комната загружена!").color(NamedTextColor.GREEN));
        }
        else {
            player.sendMessage(Component.text("При загрузке комнаты произошла ошибка считывания маркировки. Подробнее смотрите в консоли сервера!").color(NamedTextColor.RED));
        }
    }
    private void placeStructure(String type, String name, Player player) {
        if (placeStructure(type, name, new BlockLocation(player.getLocation()))) {
            player.sendMessage(Component.text("Placed successful").color(NamedTextColor.GREEN));
        }
        else {
            player.sendMessage(Component.text("No rooms with given name!"));
        }
    }
    private boolean placeStructure(String type, String name, BlockLocation location) {
        return switch (type) {
            case "room" -> rooms.placeStructure(name, location);
            case "tunnel" -> tunnels.placeStructure(name, location);
            default -> false;
        };
    }
    private void deleteStructure(String type, String name, Player player) {
        if (deleteStructure(type, name)) {
            player.sendMessage(Component.text("Room deleted successfully!").color(NamedTextColor.GREEN));
        }
        else {
            player.sendMessage("Can not find this structure! Try to change namespace");
        }
    }
    private boolean deleteStructure(String type, String name) {
        return switch (type) {
            case "room" -> rooms.deleteStructure(name);
            case "tunnel" -> tunnels.deleteStructure(name);
            default -> false;
        };
    }
    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, String @NotNull [] args) {
        Player player = (Player)commandSourceStack.getSender();
        if (args.length>=2&&args[0].equalsIgnoreCase("add")) {
            if (args.length==3) {
                NamespacedKey key = NamespacedKey.fromString(args[2]);
                Structure structure = StructureUtils.load(key);
                if (structure==null) {
                    player.sendMessage("Can not find this structure! Try to change namespace");
                    return;
                }
                loadStructure(player, structure, args[1]);
                return;
            }
            giveMarkupAxe(player);
            sessions.put(player.getUniqueId(), new Session(args[1]));
        }
        else if (args.length==3&&args[0].equalsIgnoreCase("place")) {
            placeStructure(args[1], args[2], player);
        }
        else if (args.length==3&&args[0].equalsIgnoreCase("delete")) {
            deleteStructure(args[1], args[2], player);
        }
        else if (args[0].equalsIgnoreCase("test")) {
            Structure structure = Bukkit.getStructureManager().createStructure();
            structure.fill(player.getLocation(), player.getLocation().clone().add(1, 1, 1), true);
            StructureUtils.place(structure, player.getLocation().add(0, 3, 0));
        }
    }

    @Override
    public Collection<String> suggest(@NotNull CommandSourceStack commandSourceStack, String @NotNull [] args) {
        if (args.length==0||args.length==1) {
            return List.of("add", "place", "delete");
        }
        if (args.length==2) {
            return List.of("room", "tunnel");
        }
        if (args.length==3) {
            if (args[0].equalsIgnoreCase("add")) {
                Collection<String> keys = Bukkit.getStructureManager().getStructures().keySet().stream()
                        .map(NamespacedKey::asString)
                        .toList();
                return StringUtil.copyPartialMatches(args[2], keys, new ArrayList<>());
            }
            return StringUtil.copyPartialMatches(args[2], getAllNames(args[1]), new ArrayList<>());
        }
        return List.of();
    }

    @Override
    public boolean canUse(@NotNull CommandSender sender) {
        return sender instanceof Player&&sender.isOp();
    }

    private class Session implements MarkupAxable.Session {
        private final String type;
        private Location first;
        private Location second;
        public Session(String type) {
            this.type=type;
        }
        public void saveStructure(Player player) {
            Structure structure = StructureUtils.saveStructure(new ImmutableBox(new BlockLocation(first), new BlockLocation(second)));
            loadStructure(player, structure, type);
        }
        @Override
        public boolean removeByClicked(Location location, Player player) {
            if (first==null) {
                first = location;
                return false;
            }
            second = location;
            saveStructure(player);
            return true;
        }
    }
}
