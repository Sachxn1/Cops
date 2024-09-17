import org.bukkit.plugin.java.JavaPlugin;

public class CopsMain extends JavaPlugin {


    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("NightenMC Cops enabled.");
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        this.getCommand("acops").setExecutor(new AdminCommands(this));

    }
    @Override
    public void onDisable() {
        getLogger().info("NightenMC Cops disabled.");
    }


}

