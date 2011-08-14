package me.pirogoeth.slimypistons.Events;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import java.util.logging.Logger;

import me.pirogoeth.slimypistons.SlimyPistons;

public class PlayerEventListener extends PlayerListener {
    public static SlimyPistons plugin;
    Logger log = Logger.getLogger("Minecraft");
    public PlayerEventListener (SlimyPistons instance)
    {
        plugin = instance;
    }
    public void onPlayerInteract (PlayerInteractEvent event)
    {
        /**
         * Target item codes
         *
         * Watch:
         * 339 - Paper
         * 341 - Slimeball
         *
         * Change:
         * 29 - Sticky Piston
         * 33 - Piston
         */
        Player player = event.getPlayer();
        Block clicked_b = event.getClickedBlock();
        if (clicked_b.getTypeId() == 33)
        {
            // this is the block we're watching for
            ItemStack player_h = player.getItemInHand();
            if (player_h.getTypeId() == 341)
            {
                // the correct item is in hand, change the block.
                byte rx_dat = clicked_b.getData();
                clicked_b.setTypeId(29);
                clicked_b.setData((Byte) rx_dat, true);
            }
        }
        else if (clicked_b.getTypeId() == 29)
        {
            // this is a sticky piston.
            ItemStack player_h = player.getItemInHand();
            if (player_h.getTypeId() == 339)
            {
                // this is what we're watching for
                byte rx_dat = clicked_b.getData();
                clicked_b.setTypeId(33);
                clicked_b.setData((Byte) rx_dat, true);
            }
        }
    }
}