package me.benrobson.zander.commands.moderation;

import com.google.common.collect.Sets;
import me.benrobson.zander.ConfigurationManager;
import me.benrobson.zander.ZanderBungeeMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Set;
import java.util.UUID;

public class staffchat extends Command implements Listener {
    private ZanderBungeeMain plugin;
    public static String toggleMessage = "&cYou have &7{toggle} &cthe staff chat.";

    private Set<UUID> toggled;

    public staffchat(ZanderBungeeMain plugin) {
        super("staffchat");
        this.toggled = Sets.newHashSet();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length < 1) {
            player.sendMessage(new TextComponent(ChatColor.RED + "You cannot send an empty message."));
            return;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            if (getToggled().contains(player.getUniqueId())) {
                getToggled().remove(player.getUniqueId());
            } else {
                getToggled().add(player.getUniqueId());
            }
            player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', toggleMessage.replace("{toggle}", (getToggled().contains(player.getUniqueId()) ? "joined" : "left")))));
            return;
        }

        String message = getMessage(args, 0);
        String server = player.getServer().getInfo().getName();

        for (ProxiedPlayer onlineplayer : ProxyServer.getInstance().getPlayers()) {
            if (onlineplayer.hasPermission("staffchat.use")) {
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getConfig().getString("prefix.staffchat") + " " + player.getName() + " " + server + ": " + message)));
            }
        }
    }

    @EventHandler
    public void onEvent(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (!event.getMessage().startsWith("/") && getToggled().contains(player.getUniqueId())) {
            event.setCancelled(true);
            String message = event.getMessage();
            String server = player.getServer().getInfo().getName();

            for (ProxiedPlayer onlineplayer : ProxyServer.getInstance().getPlayers()) {
                if (onlineplayer.hasPermission("staffchat.use")) {
                    player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getConfig().getString("prefix.staffchat") + " " + player.getName() + " " + server + ": " + message)));
                }
            }
        }
    }

    @EventHandler
    public void onEvent(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (getToggled().contains(player.getUniqueId())) {
            getToggled().remove(player.getUniqueId());
        }
    }

    private String getMessage(String[] args, int x) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = x; i < args.length; i++) {
            stringBuilder.append(args[i]).append( x >= args.length - 1 ? "" : " " );
        }
        return stringBuilder.toString();
    }

    private Set<UUID> getToggled() {
        return this.toggled;
    }
}