package thor.catacombs.application.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import org.bukkit.plugin.Plugin;

public interface CommandRegisterStrategy {
    boolean register(BasicCommand command, String name, String description, Plugin plugin);
}
