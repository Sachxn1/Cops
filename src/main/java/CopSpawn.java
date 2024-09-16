import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

public class CopSpawn {

    //logic for spawning in cops and making them chase for x amount of time
    public void copSpawner(Player p, Location l) {

        PigZombie cops[] = new PigZombie[3];

        for (int i = 0; i < 3; i++) { //spawn in cops and chase player
            cops[i] = (PigZombie) l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
            cops[i].setTarget(p);
            cops[i].setCustomName(ChatColor.BLUE + "Cop");
        }

        //timer to make cops chase for 5 seconds, then despawn them
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    cops[i].remove();
                }
                p.sendMessage(ChatColor.BLUE + "[Cops]" + ChatColor.GRAY + " You got away this time.");
            }
        }, 10000);

    }

}
