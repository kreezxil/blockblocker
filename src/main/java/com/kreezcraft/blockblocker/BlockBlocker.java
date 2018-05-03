package com.kreezcraft.blockblocker;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = BlockBlocker.MODID,
        version = BlockBlocker.VERSION,
        name = BlockBlocker.NAME,
        acceptableRemoteVersions = "*")
public class BlockBlocker {
    public static final String MODID = "blockblocker";
    public static final String NAME = "No Op Spawn Protection";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(clientSide = "com.kreezcraft.blockblocker.ClientProxy", serverSide = "com.kreezcraft.blockblocker.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static BlockBlocker instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

// the commands work they just don't appear to modify the config file like they should
    // or I'm getting confused, gonna release what does work tho
    // I really don't want the commands in place until I can sync the config to the client
//    @Mod.EventHandler
//    public void serverLoad(FMLServerStartingEvent event) {
//    	event.registerServerCommand(new CommandSpawnProtect());
//    	event.registerServerCommand(new CommandAllow());
//    	event.registerServerCommand(new CommandDimension());
//    	event.registerServerCommand(new CommandRadius());
//    }

}
