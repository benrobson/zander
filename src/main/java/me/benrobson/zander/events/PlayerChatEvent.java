package me.benrobson.zander.events;

import me.benrobson.zander.ZanderBungeeMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class PlayerChatEvent implements Listener {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @EventHandler
    public void PlayerOnJoin(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        String ChatMessage = event.getMessage();
        String[] words = ChatMessage.split(" ");

        //
        // Swear Filter
        //
        List<String> bannedWords = plugin.configurationManager.getConfig().getStringList("bannedWords");

        boolean bannedshouldBreak = false;
        for (String word : words) {
            if (bannedshouldBreak) break;
            for (String badWord : bannedWords) {
                if (word.toLowerCase().equals(badWord.toLowerCase())) {
                    event.setCancelled(true);
                    player.sendMessage(new TextComponent(ChatColor.RED + "You are not allowed to swear on this Server."));
                    System.out.println(badWord + " is a banned word.");
                    bannedshouldBreak = true;
                    break;
                }
            }
        }

        //
        // Link Filter
        //
        List<String> filteredLinks = plugin.configurationManager.getConfig().getStringList("filteredLinks");

        boolean linksshouldBreak = false;
        for (String word : words) {
            if (linksshouldBreak) break;

            for (String filterLink : filteredLinks) {
                if (word.toLowerCase().contains(filterLink.toLowerCase())) {
                    if (word.toLowerCase().contains(plugin.configurationManager.getConfig().getString("web.siteaddress"))) return;
                    event.setCancelled(true);
                    player.sendMessage(new TextComponent(ChatColor.RED + "You are not allowed to advertise on this Server."));

                    System.out.println(filterLink + " is a part of or is an advertising link.");

                    linksshouldBreak = true;
                    break;
                }
            }
        }
    }
}