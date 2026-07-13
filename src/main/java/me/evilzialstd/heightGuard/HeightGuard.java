package me.evilzialstd.heightGuard;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeightGuard extends JavaPlugin implements Listener {

    private int minY;
    private int maxY;
    private String textError;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);

        // Information from the config
        minY = getConfig().getInt("min-y");
        maxY = getConfig().getInt("max-y");
        textError = getConfig().getString("text-error");

        getLogger().info("enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("disabled.");
    }

    private boolean isOutSideZone(Block block) {
        int y = block.getY();
        return y < minY || y > maxY;
    }



    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (isOutSideZone(event.getBlock())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(textError);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (isOutSideZone(event.getBlock())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(textError);
        }
    }
}
