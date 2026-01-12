package thor.catacombs.application.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;

public enum GameCommandRegister implements CommandRegisterStrategy {
    INSTANCE;

    @Override
    public boolean register(BasicCommand command, String name, String description, Plugin plugin) {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> commands.registrar().register(name, description, command));
        return true;
    }
}
