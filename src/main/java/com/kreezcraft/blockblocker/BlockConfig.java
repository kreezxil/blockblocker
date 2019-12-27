package com.kreezcraft.blockblocker;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class BlockConfig {

	public static Configuration configuration;

	public static int[] dontInteract;
	public static int[] dontHarvest;
	public static int[] dontPlace;

	public static void init(String configDir) {

		if (configuration == null) {
			File path = new File(configDir + "/" + BlockBlocker.MODID + ".cfg");

			configuration = new Configuration(path);
			loadConfiguration();
		}

	}

	private static void loadConfiguration() {

		dontHarvest = configuration
				.get(Configuration.CATEGORY_GENERAL, "dontHarvest", new int[] {}, "Block ids to not allow harvesting")
				.getIntList();

		dontInteract = configuration
				.get(Configuration.CATEGORY_GENERAL, "dontInteract", new int[] {}, "Block ids to not allow interaction")
				.getIntList();

		dontPlace = configuration
				.get(Configuration.CATEGORY_GENERAL, "dontPlace", new int[] {}, "Block ids to not allow placing")
				.getIntList();

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
