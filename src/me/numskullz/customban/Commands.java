package me.numskullz.customban;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import net.minecraft.server.v1_8_R3.CommandExecute;

public class Commands extends CommandExecute implements Listener, CommandExecutor {

	private Plugin plugin = CustomBan.getPlugin(CustomBan.class);

	public String cb = "cb";

	private String args0;
	private String args1;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		plugin.getServer().getConsoleSender().sendMessage("Working4");
		if (sender instanceof Player) {
			plugin.getServer().getConsoleSender().sendMessage("Working5");
			if (cmd.getName().equalsIgnoreCase(cb)) {
				plugin.getServer().getConsoleSender().sendMessage("Working6");
				if (args.length == 1) {
					plugin.getServer().getConsoleSender().sendMessage("Working7");
					Player player = (Player) sender;

					newInventory(player);

					this.args0 = args[0];

					plugin.getConfig().set("Player_Save", args0);
				} else if (args.length >= 2) {
					plugin.getServer().getConsoleSender().sendMessage("Working8");
					Player player = (Player) sender;

					newInventory(player);

					this.args0 = args[0];
					this.args1 = args[1];

					plugin.getConfig().set("Player_Save", args0);
					plugin.getConfig().set("Reason_Save", getReason(args));
					plugin.saveConfig();
				}
			}
		}
		return false;
	}

	public void newInventory(Player player) {

		Inventory inv = plugin.getServer().createInventory(null, 9, ChatColor.DARK_RED + "CustomBan GUI");

		ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta emptyMeta = empty.getItemMeta();
		emptyMeta.setDisplayName(" ");
		emptyMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		empty.setItemMeta(emptyMeta);

		ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta confirmMeta = confirm.getItemMeta();
		confirmMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		confirmMeta.setDisplayName(ChatColor.GREEN + "Confirm");
		confirm.setItemMeta(confirmMeta);

		ItemStack cancel = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta cancelMeta = cancel.getItemMeta();
		cancelMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		cancelMeta.setDisplayName(ChatColor.RED + "Cancel");
		cancel.setItemMeta(cancelMeta);

		inv.setItem(0, empty);
		inv.setItem(1, empty);
		inv.setItem(2, empty);
		inv.setItem(3, confirm);
		inv.setItem(4, empty);
		inv.setItem(5, cancel);
		inv.setItem(6, empty);
		inv.setItem(7, empty);
		inv.setItem(8, empty);

		player.openInventory(inv);
	}

	public static int wordLength(String s) {

		int wordCount = 0;

		boolean word = false;
		int endOfLine = s.length() - 1;

		for (int i = 0; i < s.length(); i++) {
			if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
				word = true;
			} else if (!Character.isLetter(s.charAt(i)) && word) {
				wordCount++;
				word = false;
			} else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
				wordCount++;
			}
		}
		return wordCount;
	}

	public String getReason(String[] words) {
		int counter = 0;
		StringBuilder reason = new StringBuilder();
		reason = reason.append("");
		for (String s : words) {
			if (counter >= 1) {
				reason = reason.append(s + " ");
			}
			counter++;
		}
		return reason.toString();
	}

	public String getArgs0() {
		return this.args0;
	}

	public String getArgs1() {
		return this.args1;
	}

}
