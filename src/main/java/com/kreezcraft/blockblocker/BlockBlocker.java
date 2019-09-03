package com.kreezcraft.blockblocker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("blockblocker")
public class BlockBlocker
{
    public BlockBlocker() {
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BlockConfig.spec);
        MinecraftForge.EVENT_BUS.register(new BlockingHandler());
    }
}