package com.kreezcraft.blockblocker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("blockblocker")
public class BlockBlocker
{
    public static final Logger LOGGER = LogManager.getLogger();
    public BlockBlocker() {
        MinecraftForge.EVENT_BUS.register(new BlockingHandler());
        BlockConfig.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        BlockConfig.load();
    }

}