package thor.presentation;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public interface CustomCommand {
    String getName();

    void build(LiteralArgumentBuilder<CommandSourceStack> builder);
}
