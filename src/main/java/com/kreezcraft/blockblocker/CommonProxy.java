package com.kreezcraft.blockblocker;

import java.io.File;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;

	public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "blockblocker.cfg"));
        Config.readConfig();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
   }

    
    @SubscribeEvent
 //   @SideOnly(Side.SERVER)
    public static BreakEvent thoseDarnBlocks(BreakEvent event) {
    	
    	Block theBlock = event.getState().getBlock();
    	
    	String blockID = theBlock.getRegistryName().toString();
    	
		if(ArrayUtils.contains(Config.dontHarvest, blockID)) {
			event.setCanceled(true);
		}
		
    	return event;
    }

    @SubscribeEvent
    public static PlaceEvent snailRider(PlaceEvent event) {
    	
    	Block theBlock = event.getState().getBlock();
    	
    	String blockID = theBlock.getRegistryName().toString();
    	
		if(ArrayUtils.contains(Config.dontPlace, blockID)) {
			event.setCanceled(true);
			
		}
    	
    	return event;
    }

//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public void checkPlacement(RightClickItem event) {
//        ItemStack stack = event.getItemStack();
//        if (stack.isEmpty()) { return; }
//        Block block = Block.getBlockFromItem(stack.getItem());
//        if (block == null || !(block instanceof BlockRedstoneTorch)) { return; }
//        World world = event.getWorld();
//        IBlockState state = world.getBlockState(event.getPos());
//        if (!(state.getBlock() instanceof BlockPillar)) { return; }
//        if (event.isCancelable() && !event.isCanceled()) { event.setCanceled(true); }
//    }    

}

