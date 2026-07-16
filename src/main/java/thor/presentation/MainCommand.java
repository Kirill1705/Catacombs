package thor.presentation;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.List;

@RequiredArgsConstructor
public class MainCommand {
    private final List<CustomCommand> subcommands;
    private final Plugin plugin;

    public void registerCommands(Plugin plugin) {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            Commands commands = event.registrar();
            commands.register(create());
        });
    }

    private LiteralCommandNode<CommandSourceStack> create() {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(plugin.getName());
        for (CustomCommand command: subcommands) {
            LiteralArgumentBuilder<CommandSourceStack> subNode = Commands.literal(command.getName());
            command.build(subNode);
            root.then(subNode);
        }
        return root
                .requires(source -> source.getSender().isOp())
                .build();
    }
}
