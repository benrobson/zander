package net.craftingforchrist.zander;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigurationManager {
    private static ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Config
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Configuration config;
    public static Configuration initConfig() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) if (!dataFolder.mkdirs()) plugin.getLogger().info("Failed to create configuration folder.");

        File configFile = new File(dataFolder, "config.yml");
        if (!configFile.exists()) {
            try {
                if (!configFile.createNewFile()) {
                    plugin.getLogger().info("Unable to create configuration folder.");
                }
                InputStream in = plugin.getClass().getClassLoader().getResourceAsStream("config.yml");
                FileOutputStream ou = new FileOutputStream(configFile);

                ByteStreams.copy(in, ou);
                ou.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
            config = provider.load(new File(dataFolder, "config.yml"));
            provider.save(config, new File(dataFolder, "config.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return config;
    }

    public static Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration newConfig) {
        config = newConfig;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Database
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Configuration database;
    public static Configuration initDatabase() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) if (!dataFolder.mkdirs()) plugin.getLogger().info("Failed to create configuration folder.");

        File databaseFile = new File(dataFolder, "database.yml");
        if (!databaseFile.exists()) {
            try {
                if (!databaseFile.createNewFile()) {
                    plugin.getLogger().info("Unable to create configuration folder.");
                }
                InputStream in = plugin.getClass().getClassLoader().getResourceAsStream("database.yml");
                FileOutputStream ou = new FileOutputStream(databaseFile);

                ByteStreams.copy(in, ou);
                ou.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
            database = provider.load(new File(dataFolder, "database.yml"));
            provider.save(database, new File(dataFolder, "database.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return database;
    }

    public static Configuration getDatabase() {
        return database;
    }

    public void setDatabase(Configuration newDatabase) {
        database = newDatabase;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Filter
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Configuration filter;
    public static Configuration initFilter() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) if (!dataFolder.mkdirs()) plugin.getLogger().info("Failed to create configuration folder.");

        File filterFile = new File(dataFolder, "filter.yml");
        if (!filterFile.exists()) {
            try {
                if (!filterFile.createNewFile()) {
                    plugin.getLogger().info("Unable to create configuration folder.");
                }
                InputStream in = plugin.getClass().getClassLoader().getResourceAsStream("filter.yml");
                FileOutputStream ou = new FileOutputStream(filterFile);

                ByteStreams.copy(in, ou);
                ou.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
            filter = provider.load(new File(dataFolder, "filter.yml"));
            provider.save(filter, new File(dataFolder, "filter.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filter;
    }

    public static Configuration getFilter() {
        return filter;
    }

    public void setFilter(Configuration newFilter) {
        filter = newFilter;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Announcements
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Configuration announcements;
    public static Configuration initAnnouncments() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) if (!dataFolder.mkdirs()) plugin.getLogger().info("Failed to create configuration folder.");

        File announcementsFile = new File(dataFolder, "announcements.yml");
        if (!announcementsFile.exists()) {
            try {
                if (!announcementsFile.createNewFile()) {
                    plugin.getLogger().info("Unable to create configuration folder.");
                }
                InputStream in = plugin.getClass().getClassLoader().getResourceAsStream("announcements.yml");
                FileOutputStream ou = new FileOutputStream(announcementsFile);

                ByteStreams.copy(in, ou);
                ou.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
            announcements = provider.load(new File(dataFolder, "announcements.yml"));
            provider.save(announcements, new File(dataFolder, "announcements.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return announcements;
    }

    public static Configuration getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Configuration newAnnouncements) {
        announcements = newAnnouncements;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MOTD
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Configuration motd;
    public static Configuration initMotd() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) if (!dataFolder.mkdirs()) plugin.getLogger().info("Failed to create configuration folder.");

        File motdFile = new File(dataFolder, "motd.yml");
        if (!motdFile.exists()) {
            try {
                if (!motdFile.createNewFile()) {
                    plugin.getLogger().info("Unable to create configuration folder.");
                }
                InputStream in = plugin.getClass().getClassLoader().getResourceAsStream("motd.yml");
                FileOutputStream ou = new FileOutputStream(motdFile);

                ByteStreams.copy(in, ou);
                ou.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
            motd = provider.load(new File(dataFolder, "motd.yml"));
            provider.save(motd, new File(dataFolder, "motd.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return motd;
    }

    public static Configuration getMotd() {
        return motd;
    }

    public void setMotd(Configuration newMotd) {
        motd = newMotd;
    }
}
