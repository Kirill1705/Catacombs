package thor.catacombs;

import org.bukkit.plugin.java.JavaPlugin;
import thor.catacombs.application.PluginContext;
import thor.usefulUtils.utils.StructureUtils;

public final class Catacombs extends JavaPlugin {
    @Override
    public void onEnable() {
        StructureUtils.loadStructures();
        PluginContext context = new PluginContext(this);
    }
    @Override
    public void onDisable() {

    }
}

