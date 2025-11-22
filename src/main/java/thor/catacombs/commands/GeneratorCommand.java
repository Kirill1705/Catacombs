package thor.catacombs.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import thor.catacombs.commands.structureHolder.StructureLoaderFacade;
import thor.catacombs.generator.map.CatacombsGeneratorCreator;
import thor.catacombs.generator.map.GameMap;
import thor.catacombs.generator.map.MapGenerator;
import thor.usefulUtils.utils.dataStructures.BlockLocation;

public class GeneratorCommand extends MarkupAxable implements BasicCommand {
    private final StructureLoaderFacade holder;
    private final CatacombsGeneratorCreator creator;

    public GeneratorCommand(Plugin plugin, StructureLoaderFacade holder, CatacombsGeneratorCreator creator) {
        super(plugin);
        this.holder = holder;
        this.creator = creator;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        Player player = (Player)commandSourceStack.getSender();
        giveMarkupAxe(player);
        sessions.put(player.getUniqueId(), new Session());
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender.isOp() && sender instanceof Player;
    }

    private class Session implements MarkupAxable.Session {

        @Override
        public boolean removeByClicked(Location location, Player player) {
            try {
                MapGenerator generator = creator.create(holder.getRooms(), holder.getTunnels());
                GameMap map = generator.createMap(new BlockLocation(location));
                map.place();
                player.sendMessage(Component.text("Map placed successful!").color(NamedTextColor.GREEN));
            }
            catch (Exception e) {
                player.sendMessage(Component.text("Map placed with exception! "+e.getLocalizedMessage()).color(NamedTextColor.RED));
                e.printStackTrace();
            }
            return true;
        }
    }
}
