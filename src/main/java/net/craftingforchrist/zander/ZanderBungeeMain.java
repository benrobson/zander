package net.craftingforchrist.zander;

import net.craftingforchrist.zander.commands.*;
import net.craftingforchrist.zander.commands.servers.*;
import net.craftingforchrist.zander.discord.DiscordMain;
import net.craftingforchrist.zander.events.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ZanderBungeeMain extends Plugin implements Listener {
    private static ZanderBungeeMain plugin;
    public static ConfigurationManager configurationManager;
    private Connection connection;

    @Override
    public void onEnable() {
        setInstance(this);
        configurationManager.initConfig(); // Create and load config.yml
        configurationManager.initDatabase(); // Create and load database.yml
        configurationManager.initFilter(); // Create and load filter.yml
        configurationManager.initAnnouncments(); // Create and load announcements.yml
        configurationManager.initMotd(); // Create and load motd.yml
        establishConnection(); // Connect to the database

        //
        // Database Query
        // End all players game sessions that are NULL.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE gamesessions SET sessionend = NOW() where sessionend is null");
            updatestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Command Registry
        getProxy().getPluginManager().registerCommand(this, new rules());
        getProxy().getPluginManager().registerCommand(this, new ping());
        getProxy().getPluginManager().registerCommand(this, new discord());
        getProxy().getPluginManager().registerCommand(this, new ranks());
        getProxy().getPluginManager().registerCommand(this, new report());
        getProxy().getPluginManager().registerCommand(this, new vote());
        getProxy().getPluginManager().registerCommand(this, new guides());
        getProxy().getPluginManager().registerCommand(this, new website());
        getProxy().getPluginManager().registerCommand(this, new seen());
        getProxy().getPluginManager().registerCommand(this, new playtime());
        getProxy().getPluginManager().registerCommand(this, new permission());

            // Servers
            getProxy().getPluginManager().registerCommand(this, new hub());
            getProxy().getPluginManager().registerCommand(this, new creative());
            getProxy().getPluginManager().registerCommand(this, new build());
            getProxy().getPluginManager().registerCommand(this, new development());
            getProxy().getPluginManager().registerCommand(this, new survival());
            getProxy().getPluginManager().registerCommand(this, new mixed());
            getProxy().getPluginManager().registerCommand(this, new events());

        // Event Registry
        getProxy().getPluginManager().registerListener(this, new PlayerOnJoin());
        getProxy().getPluginManager().registerListener(this, new PlayerOnDisconnect());
        getProxy().getPluginManager().registerListener(this, new PlayerOnVote());
        getProxy().getPluginManager().registerListener(this, new ServerListPing());
        getProxy().getPluginManager().registerListener(this, new PlayerOnServerConnect());
        getProxy().getPluginManager().registerListener(this, new TabListListener());
        getProxy().getPluginManager().registerListener(this, new PlayerChatEvent());
        getProxy().getPluginManager().registerListener(this, new PlayerOnPunish());

        // Discord Registry
        DiscordMain DiscordMain = new DiscordMain(this);
        AnnouncementManager.schedule(this);

        if (ProxyServer.getInstance().getPluginManager().getPlugin("LuckPerms") != null) {
            System.out.println(Variables.developmentprefix + " Permission support through LuckPerms active.");
        } else {
            System.out.println(Variables.developmentprefix + " No offline permission support through LuckPerms.");
        }
    }

    @Override
    public void onDisable() {
        //
        // Database Query
        // End all players game sessions that are NULL.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE gamesessions SET sessionend = NOW() where sessionend is null");
            updatestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.getLogger().info(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + ChatColor.BLUE + " Shutting down..."));
    }

    public static ZanderBungeeMain getInstance() {
        return plugin;
    }

    private static void setInstance(ZanderBungeeMain instance) {
        ZanderBungeeMain.plugin = instance;
    }

    public void establishConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + Variables.host + ":" + Variables.port + "/" + Variables.database + "?autoReconnect=" + Variables.autoReconnect + "&useSSL=" + Variables.useSSL , Variables.username, Variables.password);
            this.getLogger().info(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + ChatColor.GREEN + " Database connection was successful."));
        } catch (SQLException e) {
            this.getLogger().info(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + ChatColor.RED + " Database connection failed!"));
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            this.getLogger().info(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + ChatColor.RED + " Database connection failed!"));
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (this.connection == null) {
            establishConnection();
        } else {
            try {
                this.connection.close();
            } catch (SQLException e) {
                this.getLogger().info(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + ChatColor.RED + " Database connection failed!"));
                e.printStackTrace();
            }
            establishConnection();
        }
        return connection;
    }
}
