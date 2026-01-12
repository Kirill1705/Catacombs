package thor.catacombs.application;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import thor.catacombs.commands.structureHolder.StructureLoaderFacade;
import thor.catacombs.game.configurators.AttributesConfigurator;
import thor.catacombs.game.configurators.GameAttributesConfigurator;
import thor.usefulUtils.utils.OtherUtils;

public class PluginContext {
    public PluginContext(Plugin plugin) {
        AttributesConfigurator configurator = new GameAttributesConfigurator();
        FileConfiguration configuration = OtherUtils.getConfig(plugin);
        MiniGameConfigFacade miniGameConfigFacade = new MiniGameConfigFacade(plugin);
        StructureLoaderFacade facade = new StructureLoaderFacade(plugin, miniGameConfigFacade.getEnvironment().getWorld(), configurator.getRegistry());
        GeneratorFacade generatorFacade = new GeneratorFacade(plugin, facade, configuration, miniGameConfigFacade.getEnvironment(), configurator.getRegistry());
        MiniGameFacade miniGameFacade = new MiniGameFacade(plugin, configuration, generatorFacade.getGeneratorCreator(), configurator, facade, miniGameConfigFacade.getEnvironment());
    }
}
