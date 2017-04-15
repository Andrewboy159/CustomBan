package me.numskullz.customban.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.numskullz.customban.CustomBan;

public class EventsClass implements Listener {

	private Plugin plugin = CustomBan.getPlugin(CustomBan.class);

	@EventHandler
	public void InvenClose(InventoryCloseEvent event) {
		Inventory inv = event.getInventory();

		if (ChatColor.stripColor(inv.getName()).equals("CustomBan GUI")) {
			plugin.getConfig().set("Player_Save", "JustAPlaceHolder");
			plugin.getConfig().set("Reason_Save", "JustAPlaceHolder");
			plugin.saveConfig();
		}
	}

	@EventHandler
	public void InvenClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		Inventory open = event.getClickedInventory();
		ItemStack item = event.getCurrentItem();

		plugin.getServer().getConsoleSender().sendMessage("Working");

		if (open == null) {
			return;
		}
		if (ChatColor.stripColor(open.getName()).equals("CustomBan GUI")) {

			plugin.getServer().getConsoleSender().sendMessage("Working2");
			event.setCancelled(true);

			if (item == null || !item.hasItemMeta()) {
				return;
			}

			if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Cancel")) {
				player.closeInventory();
				plugin.getConfig().set("Player_Save", "JustAPlaceHolder");
				plugin.getConfig().set("Reason_Save", "JustAPlaceHolder");
				plugin.saveConfig();
			} else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Confirm")) {

				String reason = plugin.getConfig().getString("Reason_Save");
				reason = ChatColor.DARK_RED + reason;
				String user = plugin.getConfig().getString("Player_Save");
				Player userp = Bukkit.getServer().getPlayer(user);

				player.closeInventory();

				try {
					if (user.equalsIgnoreCase(Bukkit.getPlayer(userp.getUniqueId()).getName())) {
						plugin.getServer().getConsoleSender().sendMessage("Working3");
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
								"kick " + userp.getName() + " " + "You were been banned by " + player.getName()
										+ " for:\n" + reason);
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
								"ban " + userp.getName() + " " + reason);
						plugin.getServer().broadcastMessage(
								ChatColor.GOLD + userp.getName() + ChatColor.RED + " has been banned!");
						plugin.getConfig().set("Player_Save", "JustAPlaceHolder");
						plugin.getConfig().set("Reason_Save", "JustAPlaceHolder");
						plugin.saveConfig();
					}
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + "That player has never played on the server before!");
				}
			}
		}
	}
}
