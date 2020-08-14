package me.benrobson.zander;

import me.benrobson.zander.commands.*;
import me.benrobson.zander.commands.moderation.staffchat;
import me.benrobson.zander.commands.servers.*;
import me.benrobson.zander.discord.DiscordMain;
import me.benrobson.zander.events.*;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
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
//        configurationManager.initMotd(); // Create and load motd.yml
        establishConnection(); // Connect to the database

        LuckPerms api = LuckPermsProvider.get();

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
//        getProxy().getPluginManager().registerListener(this, new PlayerOnVote());
        getProxy().getPluginManager().registerListener(this, new ServerListPing());
        getProxy().getPluginManager().registerListener(this, new PlayerOnServerConnect());
        getProxy().getPluginManager().registerListener(this, new TabListListener());

        // Discord Registry
        DiscordMain DiscordMain = new DiscordMain(this);
        AnnouncementManager.schedule(this);


        staffchat staffchat = new staffchat(this);
        getProxy().getPluginManager().registerCommand(this, new staffchat(this));
        getProxy().getPluginManager().registerListener(this, staffchat);
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
            String host = ConfigurationManager.getConfig().getString("database.host");
            String database = ConfigurationManager.getConfig().getString("database.database");
            String username = ConfigurationManager.getConfig().getString("database.username");
            String password = ConfigurationManager.getConfig().getString("database.password");

            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, username, password);
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
