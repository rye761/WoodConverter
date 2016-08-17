package com.ryebread761.woodconverter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Menu implements Listener {
    private final Inventory inv;
    private final Player player;
    private boolean isOpen;
    
    public Menu(Player p) {
        player = p;
        inv = Bukkit.createInventory(player, 9, "Wood Converter");
        isOpen = false;
        
        // Create inventory items (buttons)
        ItemStack[] buttons = new ItemStack[6];
        buttons[0] = new ItemStack(Material.LOG, 1, (short) 0);
        buttons[1] = new ItemStack(Material.LOG, 1, (short) 1);
        buttons[2] = new ItemStack(Material.LOG, 1, (short) 2);
        buttons[3] = new ItemStack(Material.LOG, 1, (short) 3);
        buttons[4] = new ItemStack(Material.LOG_2, 1, (short) 0);
        buttons[5] = new ItemStack(Material.LOG_2, 1, (short) 1);
        
        for (ItemStack button : buttons) {
            inv.addItem(button);
        }
        
        final Plugin plug = Bukkit.getPluginManager().getPlugin("WoodConverter");
        Bukkit.getPluginManager().registerEvents(this, plug);
    }
    
    public void open() {
        isOpen = true;
        player.openInventory(inv);
    }
    
    public void close() {
        if (isOpen) {
            isOpen = false;
            player.closeInventory();
            HandlerList.unregisterAll(this);
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player whoClicked = (Player) e.getWhoClicked();
        
        if (!(whoClicked == player)) {
            return;
        }
        
        if (!isOpen) {
            return;
        }
        
        e.setCancelled(true);
        
        final short toType = e.getCurrentItem().getDurability();
        final Material toMat = e.getCurrentItem().getType();
        final ItemStack target = player.getInventory().getItemInHand();
        
        // Make sure the player is holding a log!
        if (!((target.getType() == Material.LOG) || 
                (target.getType() == Material.LOG_2))) {
            whoClicked.sendMessage(ChatColor.RED + "That's not wood!");
            this.close();
            return;
        }
        
        // Make sure the item clicked is part of our menu.
        final int slot = e.getRawSlot();
        if (!(slot >= 0 && slot <= 8)) {
            whoClicked.sendMessage(ChatColor.RED + "That's not a button!");
            return;
        }
        
        target.setDurability(toType);
        target.setType(toMat);
        this.close();
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (isOpen && e.getPlayer() == player) {
            isOpen = false;
            HandlerList.unregisterAll(this);
        }
    }
    
}
