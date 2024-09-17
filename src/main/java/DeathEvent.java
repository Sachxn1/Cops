import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathEvent implements Listener {

    private final JavaPlugin plugin;

    // Constructor to pass the plugin instance (optional, but useful for accessing plugin methods if needed)
    public DeathEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        // Custom code for when the player dies
        if (event.getEntity().getKiller() instanceof Player) {
            Player killer = event.getEntity().getKiller();
            Location loc = killer.getLocation();

            //spawn cops in
            CopSpawn spawn = new CopSpawn();
            spawn.copSpawner(killer, loc);

        }
    }

}
