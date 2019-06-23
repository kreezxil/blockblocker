package com.kreezcraft.blockblocker;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockingHandler {

    /**
     * Stops Breakage
     */
    @SubscribeEvent
    public static BreakEvent thoseDarnBlocks(BreakEvent event) {
        Block theBlock = event.getState().getBlock();
        String name = theBlock.getRegistryName().toString();
        
        if (Config.GENERAL.dontHarvest.get().contains(name)) {
        	event.setCanceled(true);
        }
            
        return event;
    }

    /**
     * Stops placement
     *
     */
    @SubscribeEvent
    public static BlockEvent.EntityPlaceEvent snailRider(BlockEvent.EntityPlaceEvent event) {
        Block theBlock = event.getState().getBlock();
        String name = theBlock.getRegistryName().toString();
        
        if (Config.GENERAL.dontHarvest.get().contains(name)) {
        	event.setCanceled(true);
        }
        
        return event;
    }

    /**
     * Stops interactions
     */

    @SubscribeEvent
    public static RightClickBlock foxFire(RightClickBlock event) {
        BlockPos target = event.getPos();

        if (event.getEntityPlayer().getPosition() == target) {
            return event;
        }
        
        Block theBlock = event.getWorld().getBlockState(target).getBlock();
        String name = theBlock.getRegistryName().toString();
        
        if (Config.GENERAL.dontHarvest.get().contains(name)) {
        	event.setCanceled(true);
        }


        return event;
    }
}