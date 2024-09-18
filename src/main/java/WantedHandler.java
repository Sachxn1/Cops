import com.mongodb.client.MongoDatabase;
import org.bukkit.entity.Player;

public class WantedHandler {

    private MongoDBHandler dbHandler;

    public WantedHandler(MongoDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void deathWanted(Player victim, Player killer) {
        int killerWanted = dbHandler.getWantedLevel(killer.getUniqueId());
        killerWanted = killerWanted + 1;
        dbHandler.updateWantedLevel(killer.getUniqueId(), killerWanted); //increment players wanted level
        dbHandler.updateWantedLevel(victim.getUniqueId(), 0); //set victims wanted level to 0
        victim.sendMessage("Your wanted level is" + dbHandler.getWantedLevel(victim.getUniqueId()));
        killer.sendMessage("Your wanted level is" + dbHandler.getWantedLevel(killer.getUniqueId()));
    }

    //add method later for clearing wanted level based on a price

}
