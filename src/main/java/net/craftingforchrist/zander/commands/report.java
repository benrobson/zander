package net.craftingforchrist.zander.commands;

import com.google.common.collect.ImmutableSet;
import net.craftingforchrist.zander.Variables;
import net.craftingforchrist.zander.ZanderBungeeMain;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static net.craftingforchrist.zander.discord.DiscordMain.jda;

public class report extends Command implements TabExecutor {
    public report() {
        super("report");
    }
    private static ZanderBungeeMain plugin;

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;

            if (strings.length == 0) {
                player.sendMessage(new TextComponent(ChatColor.RED + "Please specify a player to report and the reason for it."));
                return;
            } else if (strings.length == 1) {
                player.sendMessage(new TextComponent(ChatColor.RED + "Please specify a reason for this report."));
                return;
            } else {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(strings[0]);

                player.sendMessage(new TextComponent(ChatColor.GREEN + "Your report has been sent to the Server Staff."));

                StringBuilder str = new StringBuilder();
                for (int i = 1; i < strings.length; i++) {
                    str.append(strings[i] + " ");
                }

                for (ProxiedPlayer pspp : ProxyServer.getInstance().getPlayers()) {
                    if (pspp.hasPermission("zander.reportnotify")) {
                        pspp.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "============================================\n" + Variables.reportprefix + " " + ChatColor.YELLOW + ChatColor.BOLD + "INCOMING REPORT\n" + player.getName() + " reported " + target.getDisplayName() + "\n" + ChatColor.GRAY + str.toString().trim() + "\n" + ChatColor.GOLD + "============================================")));
                    }
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Incoming In-Game Report");
                embed.setColor(Color.ORANGE);
                embed.addField("Reported Player", target.getDisplayName(), true);
                embed.addField("Reported By", player.getName(), true);
                embed.addField("Reported Reason", str.toString().trim(), false);

                TextChannel textChannel =  jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.reportchannel"), true).get(0);
                textChannel.sendMessage(embed.build()).queue();

                plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', Variables.reportprefix + " " + player.getName() + " reported " + target.getDisplayName() + " for " + ChatColor.GRAY + str.toString().trim()));
            }
            return;
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
        if( args.length > 2 || args.length == 0 ) {
            return ImmutableSet.of();
        }

        Set<String> matches = new HashSet<>();
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(search)) {
                    matches.add(player.getName());
                }
            }
        }
        return matches;
    }
}
