package me.pirogoeth.slimypistons.Util;

// bukkit
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;
// java
import java.io.File;
import java.util.logging.Logger;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
// internal
import me.pirogoeth.slimypistons.SlimyPistons;

public class Config {
    // main vars
    public static SlimyPistons plugin;
    public static Logger log = Logger.getLogger("Minecraft");
    public static String maindir = "plugins/SlimyPistons";
    // quizzical vars
    public static boolean loaded = false;
    // Configuration variables
    public static Configuration main;
    // File variables
    public static File mainf;
    // constructor
    public Config (SlimyPistons instance)
    {
        plugin = instance;
        new File(maindir).mkdir();
        // do our configuration initialisation here.
        initialise();
    }
    // File() creator
    private static File getFile(String fname)
    {
        File f = new File(maindir + File.separator + (String)fname);
        return f;
    }
    // initialise the Configuration setup
    public static boolean initialise ()
    {
        log.info("[SlimyPistons] Initialising configurations.");
        // load all of the config files
        try {
            mainf = getFile("config.yml");
        }
        catch (Exception e) {
            return false;
        }
        // instantiate the Configuration objects
        try {
            main = new Configuration(mainf);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    public static void load()
    {
        // load all of the configurations
        log.info("[SlimyPistons] Reading configurations.");
        main.load();
        if ((String) main.getProperty("version") == null)
        {
            // we have to write the config (easy..)
            log.info("[SlimyPistons] Writing default configurations.");
            main.setProperty("version", "1.0");
            main.setProperty("autoupdate", "true");
            main.save();
        }
        log.info("[SlimyPistons] Configuration succesfully loaded.");
        loaded = true;
        return;
    }
    public static void save ()
    {
        // use the save() method of all of the Configuration instances.
        main.save();
        log.info("[SlimyPistons] Saved all configurations.");
    }
    public static Configuration getMain ()
    {
        // returns the Configuration object for the main config file
        return (Configuration) main;
    }
}