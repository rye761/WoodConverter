package com.ryebread761.woodconverter;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Economy economy = null;
    public static int costPerLog = 10;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        costPerLog = this.getConfig().getInt("costPerLog");
        if (!setupEcon()) {
            Logger.getLogger("minecraft").log(Level.INFO,
                    "Economy support not enabled. Is Vault installed?");
        }
        this.getCommand("woodconverter").setExecutor(new WoodConverterCommand());
    }
    
    private boolean setupEcon() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        
        return (economy != null);
    }
}
