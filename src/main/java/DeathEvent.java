import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathEvent implements Listener {

    private final JavaPlugin plugin;
    private MongoDBHandler dbHandler;
    private WantedHandler wanted;
    private CopSpawn spawn = new CopSpawn();

    // Constructor to pass the plugin instance (optional, but useful for accessing plugin methods if needed)
    public DeathEvent(CopsMain plugin) {
        this.plugin = plugin;
        dbHandler = new MongoDBHandler(plugin.getMongoDatabase());
        wanted = new WantedHandler(dbHandler);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        // Custom code for when the player dies
        if (event.getEntity().getKiller() instanceof Player) {
            Player killer = event.getEntity().getKiller();
            Player victim = event.getEntity();
            Location victimLoc = event.getEntity().getLocation();

            //spawn cops in at victims location
            spawn.copSpawner(killer, victimLoc);

            //wanted level logic
            wanted.deathWanted(victim, killer);

        }
    }

}
