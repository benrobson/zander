package net.craftingforchrist.zander.instance.commands;

import net.craftingforchrist.zander.instance.ZanderInstanceMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class freeze implements CommandExecutor {
    ZanderInstanceMain plugin;
    public freeze(ZanderInstanceMain plugin) {
        this.plugin = plugin;
    }

    private static Map<UUID, Freezer> frozenPlayers = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("zander.freeze")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /freeze <player>");
            return true;
        }

        Player resolve = Bukkit.getPlayer(args[0]);
        if (resolve == null) {
            sender.sendMessage(ChatColor.YELLOW + "Player " + args[0] + " not found.");
            return true;
        }

        Freezer frozen = getFrozenPlayer(resolve);
        frozen.setFrozen(!frozen.isFrozen());

        if (frozen.isFrozen()) {
            sender.sendMessage(ChatColor.YELLOW + resolve.getName() + " was frozen.");
        } else {
            sender.sendMessage(ChatColor.YELLOW + resolve.getName() + " is no longer frozen.");
        }
        return true;
    }

    private Freezer getFrozenPlayer(Player player) {
        if (frozenPlayers.containsKey(player.getUniqueId())) {
            return frozenPlayers.get(player.getUniqueId());
        } else {
            return new Freezer(player, false);
        }
    }

    public class Freezer implements Listener {
        /**
         * Just in case the user logs out... I'm not sure if a different player object is given.
         */
        private UUID user;
        private boolean frozen;

        private Freezer(Player player, boolean freeze) {
            if (frozenPlayers.containsKey(player.getUniqueId())) {
                throw new IllegalArgumentException("Cannot create another freezer for " + player.getUniqueId());
            }

            this.user = player.getUniqueId();
            this.frozen = freeze;

            plugin.getServer().getPluginManager().registerEvents(this, plugin);

            frozenPlayers.put(player.getUniqueId(), this);
        }

        public Player resolvePlayer() {
            return Bukkit.getPlayer(this.user);
        }

        public void setFrozen(boolean frozen) {
            this.frozen = frozen;

            // Fly so they aren't glitching mid air.
            this.resolvePlayer().setFlying(frozen);

            this.resolvePlayer().sendMessage(frozen ? ChatColor.RED + "You have been frozen!" : ChatColor.GREEN + "You have been unfrozen!");
            this.resolvePlayer().sendTitle(frozen ? ChatColor.RED + "You have been frozen!" : ChatColor.GREEN + "You have been unfrozen!", "");
        }

        @EventHandler
        public void move(PlayerMoveEvent event) {
            if(event.getPlayer().getUniqueId().equals(this.user)) {
                // Allow moving head.
                if((event.getFrom().getX() == event.getTo().getX()) && (event.getFrom().getY() == event.getTo().getY()) && (event.getFrom().getZ() == event.getTo().getZ())) {
                    return;
                }
                // Check if it they are frozen and freeze them
                if(this.frozen) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(ChatColor.RED + "You are frozen!");
                }
            }
        }

        @EventHandler
        public void flightToggle(PlayerToggleFlightEvent event) {
            event.getPlayer().setFlying(this.isFrozen());
        }
    }

}
