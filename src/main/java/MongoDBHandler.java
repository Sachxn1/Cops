import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBHandler {

    private final MongoCollection<Document> collection;

    public MongoDBHandler(MongoDatabase database) {

        if (database == null) {
            throw new IllegalArgumentException("MongoDatabase cannot be null");
        }

        this.collection = database.getCollection("wanted_levels");
    }

    // Method to get the player's wanted level
    public int getWantedLevel(UUID uuid) {
        Document playerData = collection.find(eq("uuid", uuid.toString())).first();
        if (playerData == null) {
            addPlayer(uuid);
            return 0;
        }
        return playerData.getInteger("wanted_level", 0);
    }

    // Method to add a new player to the database
    public void addPlayer(UUID uuid) {
        Document newPlayer = new Document("uuid", uuid.toString())
                .append("wanted_level", 0)
                .append("cop_mode_enabled", false)
                .append("cop_inventory", new ArrayList<String>());
        collection.insertOne(newPlayer);
    }

    // Method to update the player's wanted level
    public void updateWantedLevel(UUID uuid, int newLevel) {
        collection.updateOne(eq("uuid", uuid.toString()), new Document("$set", new Document("wanted_level", newLevel)));
    }

    // Method to check if cop mode is enabled for the player
    public boolean isCopModeEnabled(UUID uuid) {
        Document playerData = collection.find(eq("uuid", uuid.toString())).first();
        if (playerData == null) {
            addPlayer(uuid);
            return false;
        }
        return playerData.getBoolean("cop_mode_enabled", false);
    }

    // Method to enable or disable cop mode
    public void setCopMode(UUID uuid, boolean enabled) {
        collection.updateOne(eq("uuid", uuid.toString()), new Document("$set", new Document("cop_mode_enabled", enabled)));
    }

    // Method to store the player's inventory when entering cop mode
    public void saveCopInventory(UUID uuid, List<ItemStack> inventory) {
        List<String> serializedInventory = new ArrayList<>();
        for (ItemStack item : inventory) {
            if (item != null) {
                serializedInventory.add(item.getType().name()); // You can customize this to serialize metadata too
            }
        }
        collection.updateOne(eq("uuid", uuid.toString()), new Document("$set", new Document("cop_inventory", serializedInventory)));
    }

    // Method to retrieve the player's inventory when leaving cop mode
    public List<ItemStack> getCopInventory(UUID uuid) {
        Document playerData = collection.find(eq("uuid", uuid.toString())).first();
        List<ItemStack> inventory = new ArrayList<>();
        if (playerData != null) {
            List<String> serializedInventory = (List<String>) playerData.get("cop_inventory");
            for (String itemName : serializedInventory) {
                ItemStack item = new ItemStack(org.bukkit.Material.valueOf(itemName)); // Deserialize the item
                inventory.add(item);
            }
        }
        return inventory;
    }
}
