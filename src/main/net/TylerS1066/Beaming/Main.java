package net.TylerS1066.Beaming;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.ChatColor;

public class Main extends JavaPlugin implements Listener{

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("beam")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players are allowed to use this command");
                return false;
            } else {
                Player player = (Player) sender;
                player.setMetadata("beaming", new FixedMetadataValue(this,true));
                player.leaveVehicle();
                if (args.length >= 1 && args[0].equalsIgnoreCase("scotty")) {
                    player.chat("Beam me up scotty!");
                }
                sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.YELLOW + "Beaming" + ChatColor.DARK_BLUE + "]" + ChatColor.RED + "You beamed to your ship!");
                		
                player.setHealth(0);
                player.spigot().respawn();
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(p.hasMetadata("beaming") && p.getMetadata("beaming").get(0).asBoolean() ){
                e.setDeathMessage(ChatColor.DARK_BLUE + "[" + ChatColor.YELLOW + "Beaming" + ChatColor.DARK_BLUE + "]"+ ChatColor.RED + p.getDisplayName() + ChatColor.RED + " beamed to their ship");
                p.setMetadata("beaming", new FixedMetadataValue(this,false));
            }
        }
    }
}