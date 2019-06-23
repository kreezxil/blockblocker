package com.kreezcraft.blockblocker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;
    
    private static Map<String,List<Integer>> place = new HashMap<>();
    private static Map<String,List<Integer>> harvest = new HashMap<>();
    private static Map<String,List<Integer>> interact = new HashMap<>();

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent event) {
        if (config.hasChanged()) {
            config.save();
        }
    }
    
    @SubscribeEvent
    public static void world(WorldEvent.Load event){
    	for(String in: Config.dontInteract){
    		String[] tmp = in.split("/");
    		
    		if(tmp.length != 2){
    			throw new RuntimeException("Invalid config at " + in + " in no-interact");
    		}
    		String name = tmp[0];
    		int meta = Integer.valueOf(tmp[1]);
    		
    		if(interact.containsKey(name)){
    			List<Integer> tmp1 = interact.get(name);
    			tmp1.add(meta);
    			interact.put(name, tmp1);
    		} else {
    			List<Integer> tmp1 = new ArrayList<Integer>();
    			tmp1.add(meta);
    			interact.put(name, tmp1);
    		}
    	}
    	for(String in: Config.dontHarvest){
    		String[] tmp = in.split("/");
    		
    		if(tmp.length != 2){
    			throw new RuntimeException("Invalid config at " + in + " in no-harvest");
    		}
    		String name = tmp[0];
    		int meta = Integer.valueOf(tmp[1]);
    		
    		if(harvest.containsKey(name)){
    			List<Integer> tmp1 = harvest.get(name);
    			tmp1.add(meta);
    			harvest.put(name, tmp1);
    		} else {
    			List<Integer> tmp1 = new ArrayList<Integer>();
    			tmp1.add(meta);
    			harvest.put(name, tmp1);
    		}
    		
    	}
    	for(String in: Config.dontPlace){
    		String[] tmp = in.split("/");
    		
    		if(tmp.length != 2){
    			throw new RuntimeException("Invalid config at " + in + " in no-placement");
    		}
    		String name = tmp[0];
    		int meta = Integer.valueOf(tmp[1]);
    		
    		if(place.containsKey(name)){
    			List<Integer> tmp1 = place.get(name);
    			tmp1.add(meta);
    			place.put(name, tmp1);
    		} else {
    			List<Integer> tmp1 = new ArrayList<Integer>();
    			tmp1.add(meta);
    			place.put(name, tmp1);
    		}
    	}
    }

    /**
     * Stops Breakage
     */
    @SubscribeEvent
    public static BreakEvent thoseDarnBlocks(BreakEvent event) {
        Block theBlock = event.getState().getBlock();
        String name = theBlock.getRegistryName().toString();
        int meta = theBlock.getMetaFromState(event.getWorld().getBlockState(event.getPos()));
        
        if(harvest.containsKey(name)){
        	if(harvest.get(name).contains(meta)) {
        		event.setCanceled(true);
        	}
        }
        return event;
    }

    /**
     * Stops placement
     *
     */
    @SubscribeEvent
    public static PlaceEvent snailRider(PlaceEvent event) {
        Block theBlock = event.getState().getBlock();
        String name = theBlock.getRegistryName().toString();
        int meta = theBlock.getMetaFromState(event.getWorld().getBlockState(event.getPos()));
        
        if(place.containsKey(name)){
        	if(place.get(name).contains(meta)) {
        		event.setCanceled(true);
        	}
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
        int meta = theBlock.getMetaFromState(event.getWorld().getBlockState(target));
        
        if(interact.containsKey(name)){
        	if(interact.get(name).contains(meta)) {
        		event.setCanceled(true);
        	}
        }


        return event;
    }

    public void init(FMLInitializationEvent e) {
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