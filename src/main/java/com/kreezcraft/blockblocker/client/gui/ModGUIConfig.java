package com.kreezcraft.blockblocker.client.gui;

import com.kreezcraft.blockblocker.BlockBlocker;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import static com.kreezcraft.blockblocker.BlockConfig.*;

public class ModGUIConfig extends GuiConfig {

	public ModGUIConfig(GuiScreen guiScreen) {
		super(guiScreen,
				new ConfigElement<Object>(getConfiguration().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				BlockBlocker.MODID,
				false,
				false,
				GuiConfig.getAbridgedConfigPath(getConfiguration().toString())
				);
	}
}
