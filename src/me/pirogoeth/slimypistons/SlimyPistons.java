package me.pirogoeth.slimypistons;

// bukkit imports
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
// Permissions imports
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
// Java core util
import java.util.logging.Logger;
import java.util.List;
import java.util.Map;
import java.io.File;
// internals
import me.pirogoeth.slimypistons.Events.PlayerEventListener;
import me.pirogoeth.slimypistons.Util.Permission;
import me.pirogoeth.slimypistons.Util.AutoUpdate;
import me.pirogoeth.slimypistons.Util.Config;

@SuppressWarnings("unused")
public class SlimyPistons extends JavaPlugin {
    // permissions
    public Permission permissions;
    // configuration
    public Config config = new Config(this);
    // player listener
    private final PlayerEventListener playerListener = new PlayerEventListener(this);
    // autoupdate
    private final AutoUpdate updater = new AutoUpdate(this);
    // logging
    Logger log = Logger.getLogger("Minecraft");
    // enabled
    public void onEnable () {
        this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
        log.info(String.format("[SlimyPistons] Version %s has been enabled.", this.getDescription().getVersion()));
        config.save();
        updater.doUpdate();
    }
    // disabled
    public void onDisable () {
        updater.finalise();
        log.info(String.format("[SlimyPistons] Version %s has been disabled.", this.getDescription().getVersion()));
    }
    public File fileGet () {
        return this.getFile();
    }
    public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args)
    {
        if (sender.getClass().getName().toString() == "org.bukkit.craftbukkit.command.ColouredCommandSender")
        {
            sender.sendMessage("[SlimyPistons] You need to be a player to use commands for these plugins.");
            return true;
        }
        return true;
    }
}