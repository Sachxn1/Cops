import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bukkit.plugin.java.JavaPlugin;

public class CopsMain extends JavaPlugin {

    private MongoClient mongoClient;  // MongoDB client to handle the connection
    private MongoDatabase mongoDatabase;  // MongoDB database instance

    @Override
    public void onEnable() {
        // Try to connect to MongoDB first
        if (!connectToMongoDB()) {
            // If MongoDB connection fails, disable the plugin
            getLogger().severe("MongoDB connection failed. Disabling the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        // Now that MongoDB is connected, use mongoDatabase
        MongoDBHandler mongoHandler = new MongoDBHandler(mongoDatabase); // This works because mongoDatabase is initialized
        getLogger().info("NighttenMC Cops enabled.");

        // Register events and commands only after the database is initialized
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        this.getCommand("acops").setExecutor(new AdminCommands(this));
    }

    @Override
    public void onDisable() {
        // Close the MongoDB connection when the plugin is disabled
        if (mongoClient != null) {
            mongoClient.close();
            getLogger().info("MongoDB connection closed.");
        }
        getLogger().info("NighttenMC Cops disabled.");
    }

    /**
     * Connect to MongoDB and return whether it was successful.
     *
     * @return true if connection is successful, false otherwise.
     */
    public boolean connectToMongoDB() {
        try {
            String connectionString = getConfig().getString("mongodb.connection-string");
            mongoClient = MongoClients.create(connectionString);  // Initialize the MongoClient

            // Connect to the database specified in the config
            String dbName = getConfig().getString("mongodb.database");
            mongoDatabase = mongoClient.getDatabase(dbName);  // Now mongoDatabase is initialized

            if (mongoDatabase != null) {
                getLogger().info("Connected to MongoDB: " + dbName);
                return true;
            } else {
                getLogger().severe("Could not connect to MongoDB. Database is null.");
                return false;
            }
        } catch (Exception e) {
            getLogger().severe("Failed to connect to MongoDB: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Method to return the MongoDatabase instance (useful if you need it in other parts of your plugin)
    public MongoDatabase getMongoDatabase() {
        if (mongoDatabase == null) {
            getLogger().severe("MongoDatabase is not initialized yet.");
        }
        return mongoDatabase;
    }
}
