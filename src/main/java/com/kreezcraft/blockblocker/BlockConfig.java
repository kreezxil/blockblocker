package com.kreezcraft.blockblocker;

import java.io.File;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class BlockConfig {

	public static Configuration configuration;

	public static String[] dontInteract;
	public static String[] dontHarvest;
	public static String[] dontPlace;
	public static boolean debugMode;

	public static void init(String configDir) {

		if (configuration == null) {
			File path = new File(configDir + "/" + BlockBlocker.MODID + ".cfg");

			configuration = new Configuration(path);
			loadConfiguration();
		}

	}

	private static void loadConfiguration() {
		
		debugMode = configuration
				.get(Configuration.CATEGORY_GENERAL, "debugMode", false, "Is debugMode active?")
				.getBoolean();

		dontHarvest = configuration
				.get(Configuration.CATEGORY_GENERAL, "dontHarvest", new String[] {}, "Block ids to not allow harvesting\nformat is ###:#")
				.getStringList();

		dontInteract = configuration
				.get(Configuration.CATEGORY_GENERAL, "dontInteract", new String[] {}, "Block ids to not allow interaction\nformat is ###:#")
				.getStringList();

		dontPlace = configuration
				.get(Configuration.CATEGORY_GENERAL, "dontPlace", new String[] {}, "Block ids to not allow placing\nformat is ###:#")
				.getStringList();

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(BlockBlocker.MODID)) {
			loadConfiguration();
		}
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

}
