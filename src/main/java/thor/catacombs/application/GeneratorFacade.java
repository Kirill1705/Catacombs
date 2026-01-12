package thor.catacombs.application;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import thor.catacombs.commands.GeneratorCommand;
import thor.catacombs.commands.MarkupCommand;
import thor.catacombs.commands.structureHolder.StructureLoaderFacade;
import thor.catacombs.events.RoomRegisterEvent;
import thor.catacombs.events.creators.GameRoomCreator;
import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.generator.ItemInfoGenerator;
import thor.catacombs.generator.map.CatacombsGeneratorCreator;
import thor.catacombs.info.attributes.AttributeRegistry;
import thor.catacombs.info.block.FeelType;
import thor.emptyMiniGame.MiniGameEnvironment;

import java.util.Map;

public class GeneratorFacade {
    private final ItemGeneratorHolder itemGenerator;
    private final CatacombsGeneratorCreator generatorCreator;

    public ItemGeneratorHolder getItemGenerator() {
        return itemGenerator;
    }
    public GeneratorFacade(Plugin plugin, StructureLoaderFacade holder, FileConfiguration configuration, MiniGameEnvironment environment, AttributeRegistry registry) {
        itemGenerator = new ItemInfoGenerator(plugin.getDataFolder(), Map.of(FeelType.CHEST, "chests.yml", FeelType.BARREL, "barrels.yml"));
        GameRoomCreator creator = new GameRoomCreator(itemGenerator);
        RoomRegisterEvent roomRegisterEvent = new RoomRegisterEvent(creator, creator);
        generatorCreator = new CatacombsGeneratorCreator(roomRegisterEvent, roomRegisterEvent, configuration);
        new GeneratorCommand(plugin, holder, generatorCreator);
        new MarkupCommand(plugin, registry, environment);
    }

    public CatacombsGeneratorCreator getGeneratorCreator() {
        return generatorCreator;
    }
}
