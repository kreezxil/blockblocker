package com.kreezcraft.blockblocker;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent event) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    public void init(FMLInitializationEvent e) {
    }

    /**
     * Stops Breakage
     *
     * @param event
     * @return
     */
    @SubscribeEvent
    public static BreakEvent thoseDarnBlocks(BreakEvent event) {

        Block theBlock = event.getState().getBlock();

        String blockID = theBlock.getRegistryName().toString();

        if (ArrayUtils.contains(Config.dontHarvest, blockID)) {
            event.setCanceled(true);
        }

        return event;
    }

    /**
     * Stops placement
     * @param event
     * @return
     */
    @SubscribeEvent
    public static PlaceEvent snailRider(PlaceEvent event) {

        Block theBlock = event.getState().getBlock();
        String blockID = theBlock.getRegistryName().toString();

        if (ArrayUtils.contains(Config.dontPlace, blockID)) {
            event.setCanceled(true);
        }

        return event;
    }

    /**
     * Stops interactions
     *
     * @param event
     * @return
     */

    @SubscribeEvent
    public static RightClickBlock foxFire(RightClickBlock event) {

        BlockPos target = event.getPos();

        if (event.getEntityPlayer().getPosition() == target) {
            //this is the player, get out of here
            return event;
        }

        Block targetBlock = event.getWorld().getBlockState(target).getBlock();
        String blockID = targetBlock.getRegistryName().toString();

        if (ArrayUtils.contains(Config.dontInteract, blockID)) {
            event.setCanceled(true);
        }

        return event;
    }

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "blockblocker.cfg"));
        Config.readConfig();
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }
}