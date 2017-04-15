package me.numskullz.customban;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.numskullz.customban.events.EventsClass;

public class CustomBan extends JavaPlugin {

	@Override
	public void onEnable() {
		consoleMessage("CustomBan> Loading ...");
		consoleMessage("CustomBan> Registering Commands ...");
		registerCommands();
		consoleMessage("CustomBan> Commands Registered!");
		consoleMessage("CustomBan> Registering Events ...");
		getServer().getPluginManager().registerEvents(new EventsClass(), this);
		consoleMessage("CustomBan> Events Registered!");
		consoleMessage("CustomBan> " + ChatColor.GREEN + "Plugin has been loaded!");
		getConfig().set("Player_Save", "JustAPlaceHolder");
		getConfig().set("Reason_Save", "JustAPlaceHolder");
		saveConfig();
	}

	@Override
	public void onDisable() {
		consoleMessage("CustomBan> Unloading ...");
		consoleMessage("CustomBan> Saivng data ...");
		consoleMessage("CustomBan> " + ChatColor.RED + "Plugin has been unloaded!");
		getConfig().set("Player_Save", "JustAPlaceHolder");
		getConfig().set("Reason_Save", "JustAPlaceHolder");
		saveConfig();
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void registerCommands() {
		getCommand("cb").setExecutor(new Commands());
	}

	public void consoleMessage(String message) {
		getServer().getConsoleSender().sendMessage(message);
	}
}
