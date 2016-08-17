package com.ryebread761.woodconverter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WoodConverterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string,
            String[] strings) {
        if (!cmd.getName().equalsIgnoreCase("woodconverter")) {
            return false;
        }
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is only available to players.");
            return false;
        }
        
        final Menu m = new Menu((Player) sender);
        m.open();
        
        return true;
    }
    
}
