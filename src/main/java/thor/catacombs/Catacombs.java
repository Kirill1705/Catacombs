package thor.catacombs;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import thor.catacombs.commands.GeneratorCommand;
import thor.catacombs.commands.MarkupCommand;
import thor.catacombs.commands.structureHolder.StructureLoaderFacade;
import thor.catacombs.events.RoomRegisterEvent;
import thor.catacombs.events.creators.GameRoomCreator;
import thor.catacombs.game.Game;
import thor.catacombs.game.GameConfigurator;
import thor.catacombs.game.MyListener;
import thor.catacombs.game.configurators.*;
import thor.catacombs.generator.map.CatacombsGeneratorCreator;
import thor.catacombs.generator.map.GeneratorCreator;
import thor.catacombs.generator.ItemGeneratorHolder;
import thor.catacombs.generator.ItemInfoGenerator;
import thor.catacombs.info.block.FeelType;
import thor.emptyMiniGame.*;
import thor.lobby.commands.Team;
import thor.usefulUtils.utils.ItemUtils;
import thor.usefulUtils.utils.OtherUtils;
import thor.usefulUtils.utils.StructureUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Catacombs extends JavaPlugin {
    @Override
    public void onEnable() {
        StructureUtils.loadStructures();
        MiniGameConfig config = MiniGameConfig.builder().name("catacombs").startItem(startItem()).autoRemovePlayerByDeath(true).build();
        MiniGameEnvironment environment = new MiniGameEnvironment(config, this);
        AttributesConfigurator attributesConfigurator = new GameAttributesConfigurator();
        StructureLoaderFacade holder = new StructureLoaderFacade(this, environment.getWorld(), attributesConfigurator.getRegistry());
        FileConfiguration configuration = OtherUtils.getConfig(this);
        ItemGeneratorHolder itemGenerator = new ItemInfoGenerator(this.getDataFolder(), Map.of(FeelType.CHEST, "chests.yml", FeelType.BARREL, "barrels.yml"));
        GameRoomCreator creator = new GameRoomCreator(itemGenerator);
        RoomRegisterEvent roomRegisterEvent = new RoomRegisterEvent(creator, creator);
        Bukkit.getPluginManager().callEvent(roomRegisterEvent);
        CatacombsGeneratorCreator generatorCreator = new CatacombsGeneratorCreator(roomRegisterEvent, roomRegisterEvent, configuration);
        GeneratorCommand generatorCommand = new GeneratorCommand(this, holder, generatorCreator);
        GameFactory factory = new GameFactory(holder, new GameConfigurator(
                this,
                environment,
                OtherUtils.getConfig(this),
                attributesConfigurator,
                new GameLobbyConfigurator(environment, configuration, this),
                new GameVariableConfigurator()
        ), environment, new CatacombsGeneratorCreator(
                roomRegisterEvent,
                roomRegisterEvent,
                configuration
        ), this, configuration);
        MiniGameLoader loader = factory.getLoader();
        MarkupCommand markupCommand = new MarkupCommand(this, attributesConfigurator.getRegistry(), environment);
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register("catacombs", "Manage Catacombs", markupCommand);
            commands.registrar().register("generator", "Generate maps", generatorCommand);
        });
        loader.setTeamViewer(player -> {
            Team team = Team.getTeam(player);
            if (team==null) return List.of(player);
            Set<Player> players = team.getPlayers();
            for (Player teammate: players) {
                team.removePlayer(teammate);
            }
            return new ArrayList<>(players);
        });
        getServer().getPluginManager().registerEvents(new MyListener(loader), this);
    }
    public static ItemStack startItem() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text("Играть в катакомбы").color(NamedTextColor.YELLOW));
        ItemUtils.setCustomModelData(meta, "1");
        itemStack.setItemMeta(meta);
        itemStack.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        return itemStack;
    }
    @Override
    public void onDisable() {

    }
}

class GameFactory implements MiniGameCreator {
    private final StructureLoaderFacade holder;
    private final Configurator configurator;
    private final MiniGameLoader loader;
    private final GeneratorCreator creator;
    private final Plugin plugin;
    private final FileConfiguration config;
    public MiniGameLoader getLoader() {
        return loader;
    }

    GameFactory(StructureLoaderFacade holder, Configurator configurator, MiniGameEnvironment environment, GeneratorCreator creator, Plugin plugin, FileConfiguration config) {
        this.holder = holder;
        this.configurator = configurator;
        loader = environment.buildLoader(this);
        this.creator = creator;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public MiniGame create(Location loc) {
        return new Game(loc, creator.create(holder.getRooms(), holder.getTunnels()), configurator, plugin, loader, config);
    }
}
