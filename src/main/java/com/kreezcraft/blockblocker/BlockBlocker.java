package com.kreezcraft.blockblocker;

import com.kreezcraft.blockblocker.proxy.IProxy;
import com.kreezcraft.blockblocker.utility.LogHelper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;


@Mod(modid = BlockBlocker.MODID, name = BlockBlocker.MODNAME, version = BlockBlocker.VERSION, guiFactory = BlockBlocker.GUI_FACTORY_CLASS)
public class BlockBlocker {

	public static final String MODID = "blockblocker";
	public static final String MODNAME = "Block Blocker";
	public static final String VERSION = "1.3.1";

	public static final String CLIENT_PROXY_CLASS = "com.kreezcraft.blockblocker.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.kreezcraft.blockblocker.proxy.ServerProxy";
	
	public static final String GUI_FACTORY_CLASS = "com.kreezcraft.blockblocker.client.gui.GuiFactory";
	
	@Mod.Instance(MODID)
	public static BlockBlocker instance = new BlockBlocker();

	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		
		LogHelper.info("BlockerBlocker Pre-initializing");
		
		BlockConfig.init(e.getModConfigurationDirectory().toString());
		FMLCommonHandler.instance().bus().register(new BlockConfig());
		
		MinecraftForge.EVENT_BUS.register(new BlockingHandler());

		LogHelper.info("BlockerBlocker Pre-initializing Done");

	}

	@EventHandler
	public void init(FMLInitializationEvent e) {

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		LogHelper.info("BlockBlocker has fully initialized");
	}
}
