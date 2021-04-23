package com.jules.compass;

import org.bukkit.plugin.java.JavaPlugin;

public class Compass extends JavaPlugin {


    @Override
    public void onEnable()
    {
        getLogger().info("Compass plugin started");
        getServer().getPluginManager().registerEvents(new CompassListeners(),this);
    }

    @Override
    public void onDisable()
    {
        getLogger().info("Compass plugin closed");
    }
}
