import org.bukkit.plugin.java.JavaPlugin;

public class CopsMain extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
