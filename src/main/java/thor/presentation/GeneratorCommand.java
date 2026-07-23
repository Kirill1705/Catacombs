package thor.presentation;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver;
import io.papermc.paper.math.BlockPosition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thor.core.port.input.LocationDto;
import thor.core.port.input.MapEngineService;
import thor.core.port.input.MapPlaceOptions;
import thor.core.port.input.MapService;
import thor.core.port.mapping.dto.map.PlacedMapDto;

import java.util.UUID;

@RequiredArgsConstructor
public class GeneratorCommand implements CustomCommand{
    @Getter
    private final String name = "generate";

    private final MapService service;
    private final MapEngineService engineService;

    @Override
    public void build(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.argument("coords", ArgumentTypes.blockPosition())
                .executes(context -> execute(context, true, true))
                .then(Commands.argument("placeBedrock", BoolArgumentType.bool())
                .executes(context -> execute(context, context.getArgument("placeBedrock", boolean.class), true))
                .then(Commands.argument("fillStone", BoolArgumentType.bool())
                .executes(context -> execute(context, context.getArgument("placeBedrock", boolean.class), context.getArgument("fillStone", boolean.class))))));
    }

    private int execute(CommandContext<CommandSourceStack> context, boolean placeBedrock, boolean fillStone) {
        BlockPositionResolver positionResolver = context.getArgument("coords", BlockPositionResolver.class);
        CommandSourceStack sourceStack = context.getSource();
        try {
            BlockPosition position = positionResolver.resolve(sourceStack);
            UUID uuid = service.generateMap();
            MapPlaceOptions options = new MapPlaceOptions();
            options.setFillBedrock(placeBedrock);
            options.setFillStone(fillStone);
            engineService.placeMap(new LocationDto(sourceStack.getLocation().getWorld().getName(), position.blockX(), position.blockY(), position.blockZ()), uuid, options);
            return 1;
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
