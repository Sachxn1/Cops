import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class AdminCommands implements CommandExecutor {

    private final JavaPlugin plugin;

    public AdminCommands(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("cops.admin")) {
                // Help command
                if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                    List<String> adminMessages = plugin.getConfig().getStringList("admin-msgs.admin-undefined");
                    for (String message : adminMessages) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                }
                // Reload command
                else if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("admin-msgs.admin-reload")));
                }
                // Clear wanted command
                else if (args[0].equalsIgnoreCase("clearwanted")) {
                    if (args.length >= 2) {
                        Player target = plugin.getServer().getPlayerExact(args[1]);
                        if (target != null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("admin-msgs.admin-clear-wanted")));
                            // Add logic to clear wanted level here
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&9Cops&7] Please specify an online player."));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&9Cops&7] Please specify an online player."));
                    }
                }

                //wanted increase
                else if (args[0].equalsIgnoreCase("wantedincrease")) {
                    if (args.length >= 2) {
                        Player target = plugin.getServer().getPlayerExact(args[1]);
                        if (target != null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("admin-msgs.admin-increase-wanted")));
                            // Add logic to increase wanted level here
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&9Cops&7] Please specify an online player."));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&9Cops&7] Please specify an online player."));
                    }
                }

                //save loadout
                else if (args[0].equalsIgnoreCase("saveloadout")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("admin-msgs.admin-save-loadout")));
                    //add logic to save loadout here
                }

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("admin-msgs.no-perms")));
            }
        }
        return true;
    }

}
