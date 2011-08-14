package me.pirogoeth.slimypistons.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import org.bukkit.util.FileUtil;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.util.logging.Logger;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.bukkit.Bukkit;
import org.bukkit.util.config.Configuration;

import me.pirogoeth.slimypistons.SlimyPistons;

public class AutoUpdate {
    public File jar;
    public Logger log = Logger.getLogger("Minecraft");
    public SlimyPistons plugin;
    public Configuration main;
    public AutoUpdate (SlimyPistons instance)
    {
        plugin = instance;
    }
    // borrowed from Afforess
    public void finalise () {
        try {
            File directory = new File(Bukkit.getServer().getUpdateFolder());
            if (directory.exists()) {
                File p = new File(directory.getPath(), "SlimyPistons.jar");
                if (p.exists()) {
                    FileUtil.copy(p, plugin.fileGet());
                    p.delete();
                    log.info("[SlimyPistons] Update finalised.");
                }
            }
        }
        catch (Exception e) {}
    }
    protected int getVersion () {
        try {
            String[] split = plugin.getDescription().getVersion().split("\\.");
            return Integer.parseInt(split[0]) * 100 + Integer.parseInt(split[1]) * 10 + Integer.parseInt(split[2]);
        }
        catch (Exception e) {}
        return -1;
    }
    protected boolean checkUpdate () {
        if (plugin.config.getMain().getBoolean("autoupdate", true) == false) {
           log.info("[SlimyPistons] Auto-update is disabled.");
           return false;
        }
        try {
            URL versionfile = new URL("http://maio.me/~pirogoeth/SlimyPistons.version.txt");
            log.info("[SlimyPistons] Checking for updates..");
            BufferedReader in = new BufferedReader(new InputStreamReader(versionfile.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                String[] split = str.split("\\.");
                int version = Integer.parseInt(split[0]) * 100 + Integer.parseInt(split[1]) * 10 + Integer.parseInt(split[2]);
                if (version > getVersion()){
                   in.close();
                   log.info(String.format("[SlimyPistons] Update found. %s->%s :: Now Updating.", getVersion(), version));
                   return true;
                }
            }
            in.close();
        }
        catch (Exception e) {}
        log.info("[SlimyPistons] No updates available.");
        return false;
    }
    public void doUpdate () {
        if (!checkUpdate()) {
            return;
        }
        try {
            URL source = new URL("http://maio.me/~pirogoeth/SlimyPistons.jar");
            File directory = new File(Bukkit.getServer().getUpdateFolder());
            if (!directory.exists()) {
               directory.mkdir();
            }
            File plugin = new File(directory.getPath(), "SlimyPistons.jar");
            if (!plugin.exists()) {
                HttpURLConnection con = (HttpURLConnection)(source.openConnection());
                ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
                FileOutputStream fos = new FileOutputStream(plugin);
                fos.getChannel().transferFrom(rbc, 0, 1 << 24);
                fos.close();
            }
        }
        catch (Exception e) {}
    }
}

