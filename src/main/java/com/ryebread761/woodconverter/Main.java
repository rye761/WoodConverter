package com.ryebread761.woodconverter;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("woodconverter").setExecutor(new WoodConverterCommand());
    }
}
