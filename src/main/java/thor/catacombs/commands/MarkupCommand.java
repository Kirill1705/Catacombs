package thor.catacombs.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;
import thor.catacombs.application.commands.GameCommandRegister;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.attributes.ConfigAttribute;
import thor.emptyMiniGame.MiniGameEnvironment;
import thor.usefulUtils.dataStructures.Pair;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class MarkupCommand extends MarkupAxable implements BasicCommand {
    private final AttributeRegistry registry;
    private final Component usage;
    private final MiniGameEnvironment loader;
    public MarkupCommand(Plugin plugin, AttributeRegistry registry, MiniGameEnvironment loader) {
        super(plugin);
        this.registry=registry;
        this.loader = loader;
        StringBuilder builder = new StringBuilder();
        builder.append("/create ");
        for (String holder: registry.getHolders()) {
            builder.append(holder).append(" ");
            for (ConfigAttribute<?> attribute: registry.getNecessaryAttributesFor(holder)) {
                builder.append(attribute.getType().getName()).append(' ').append("[value] ");
            }
            for (ConfigAttribute<?> attribute: registry.getDefaultAttributesFor(holder)) {
                builder.append(attribute.getType().getName()).append('|');
            }
        }
        builder.deleteCharAt(builder.length()-1).append(" [value]\n");
        builder.append("/remove ");
        usage = Component.text(builder.toString()).color(NamedTextColor.YELLOW);
        GameCommandRegister.INSTANCE.register(this, "catacombs", "Markup structures for catacombs minigame", plugin);
    }
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Только игроки могут использовать эту команду");
            return;
        }
        if (args.length==1 && args[0].equalsIgnoreCase("tptolobby")) {
            player.teleport(new Location(loader.getWorld(), 0, 100, 0));
            player.setGameMode(GameMode.CREATIVE);
            player.setFlying(true);
            return;
        }
        if (args[0].equalsIgnoreCase("create")) {
            Set<String> holderTypes = registry.getHolders();
            if (!holderTypes.contains(args[1])) {
                player.sendMessage(usage);
                return;
            }
            try {
                sessions.put(player.getUniqueId(), new CreationSession(args));
            }
            catch (IllegalArgumentException e) {
                player.sendMessage(e.getLocalizedMessage());
                return;
            }
            giveMarkupAxe(player);
            player.sendMessage(Component.text("Кликните ЛКМ по блоку чтобы разместить разметку").color(NamedTextColor.GREEN));
        }
        else if (args[0].equalsIgnoreCase("remove")) {
            sessions.put(player.getUniqueId(), new KillDisplaySession());
            giveMarkupAxe(player);
            player.sendMessage(Component.text("ЛКМ по блоку с которого хотите убрать разметку").color(NamedTextColor.YELLOW));
        }
        else if (args[0].equalsIgnoreCase("test")) {
            player.setGameMode(GameMode.CREATIVE);
            player.setFlying(true);
            player.teleport(new Location(loader.getWorld(), 100, 100, 100));
        }
        else {
            player.sendMessage(usage);
        }
    }
    private Collection<String> suggestValues(String[] args) {
        String type = args[args.length-2];
        Optional<ConfigAttribute<?>> optional = registry.getAttributesFor(args[1]).stream().filter(attr -> attr.getType().getName().equalsIgnoreCase(type)).findFirst();
        if (optional.isEmpty()) return Collections.emptyList();
        ConfigAttribute<?> attribute = optional.get();
        return StringUtil.copyPartialMatches(args[args.length-1], attribute.getSuggestValues(), new ArrayList<>());
    }
    private Set<String> remainAttributes(String[] args, Collection<ConfigAttribute<?>> attributes) {
        Set<String> remain = attributes.stream()
                .map(attribute -> attribute.getType().getName())
                .collect(Collectors.toSet());
        for (int i = 2; i < args.length; i+=2) {
            remain.remove(args[i]);
        }
        return remain;
    }
    public Collection<String> suggestKeys(String[] args) {
        Set<String> remain = remainAttributes(args, registry.getNecessaryAttributesFor(args[1]));
        if (!remain.isEmpty())
            return StringUtil.copyPartialMatches(args[args.length-1], remain, new ArrayList<>());
        remain = remainAttributes(args, registry.getDefaultAttributesFor(args[1]));
        return StringUtil.copyPartialMatches(args[args.length-1], remain, new ArrayList<>());
    }
    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 0||args.length==1) {
            return List.of("create", "tptolobby", "remove");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
            return registry.getHolders();
        } else if (args.length >= 3 && args[0].equalsIgnoreCase("create")) {
            if (args.length % 2 == 0) {
                return suggestValues(args);
            } else {
                return suggestKeys(args);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender.isOp();
    }

    private void createTextDisplay(Location location, String text) {
        TextDisplay display = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        display.text(Component.text(text));
        display.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
        display.setTextOpacity(Byte.MAX_VALUE);
        display.setSeeThrough(true);
        display.setDefaultBackground(false);
        display.setPersistent(true);
    }

    private class CreationSession implements Session {
        private final String yamlString;
        private @Nullable Pair<String, String> findAttribute(String attribute, String[] args) {
            for (int i = 2; i < args.length-1; i+=2) {
                if (args[i].equalsIgnoreCase(attribute)) {
                    return Pair.of(args[i], args[i+1]);
                }
            }
            return null;
        }
        private void write(YamlConfiguration configuration, String key, String value) {
            try {
                int intValue = Integer.parseInt(value);
                configuration.set(key, intValue);
            } catch (NumberFormatException e) {
                try {
                    double doubleValue = Double.parseDouble(value);
                    configuration.set(key, doubleValue);
                } catch (NumberFormatException ex) {
                    configuration.set(key, value);
                }
            }
        }
        private String parseAttributes(String[] args) {
            YamlConfiguration configuration = new YamlConfiguration();
            configuration.set("type", args[1]);
            for (ConfigAttribute<?> attribute: registry.getNecessaryAttributesFor(args[1])) {
                Pair<String, String> keyValuePair = findAttribute(attribute.getType().getName(), args);
                if (keyValuePair==null) throw new IllegalArgumentException("Attribute "+attribute.getType().getName()+" is necessary for type "+args[1]);
                write(configuration, keyValuePair.first, keyValuePair.second);
            }
            for (ConfigAttribute<?> attribute: registry.getDefaultAttributesFor(args[1])) {
                Pair<String, String> keyValuePair = findAttribute(attribute.getType().getName(), args);
                if (keyValuePair==null) continue;
                write(configuration, keyValuePair.first, keyValuePair.second);
            }
            return configuration.saveToString();
        }
        public CreationSession(String[] args) {
            this.yamlString = parseAttributes(args);
        }

        @Override
        public boolean removeByClicked(Location location, Player player) {
            createTextDisplay(location.toCenterLocation(), yamlString);
            player.sendMessage(Component.text("Разметка успешно размещена!").color(NamedTextColor.GREEN));
            return true;
        }
    }
    private static class KillDisplaySession implements Session {
        @Override
        public boolean removeByClicked(Location location, Player player) {
            Collection<Entity> entities = player.getWorld().getNearbyEntities(location.getBlock().getBoundingBox());
            for (Entity entity: entities) {
                entity.remove();
            }
            player.sendMessage(Component.text("Готово!").color(NamedTextColor.GREEN));
            return true;
        }
    }
}