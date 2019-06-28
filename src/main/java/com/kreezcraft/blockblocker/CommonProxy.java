package com.kreezcraft.blockblocker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

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
    
    private static Map<String,BlockEntry> place = new HashMap<>();
    private static Map<String,BlockEntry> harvest = new HashMap<>();
    private static Map<String,BlockEntry> interact = new HashMap<>();

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent event) {
        if (config.hasChanged()) {
            config.save();
        }
    }
    
    @SubscribeEvent
    public static void world(WorldEvent.Load event){
    	for(String in: Config.dontInteract){
    		String[] tmp = in.split("\\|");
    		String[] tmp2 = tmp[1].split("\\$");
    		if(tmp2.length == 2){
    			tmp = ArrayUtils.remove(tmp, 1);
    			tmp = ArrayUtils.add(tmp, tmp2[0]);
    			tmp = ArrayUtils.add(tmp, tmp2[1]);
    		}
    		
    		if(tmp.length <= 2 && tmp.length >= 3){
    			throw new RuntimeException("Invalid config at " + in + " in no-interact");
    		}
    		
    		String name = tmp[0];
    		int meta = Integer.valueOf(tmp[1]);
    		
    		if(interact.containsKey(name)){
    			BlockEntry entry = interact.get(name);
    			entry.addMeta(meta);
    			interact.put(name, entry);
    		} else {
    			BlockEntry entry = new BlockEntry(name,meta);
    			interact.put(name, entry);
    		}
    		
    		if(tmp.length==3){
    			BlockEntry entry = interact.get(name);
    			entry.addDim(Integer.valueOf(tmp[2]));
    			interact.put(name, entry);
    		}
    	}
    	for(String in: Config.dontHarvest){
    		String[] tmp = in.split("\\|");
    		String tmp3 = tmp[1];
    		String[] tmp2 = tmp3.split("\\$");
    		if(tmp2.length == 2){
    			tmp = ArrayUtils.remove(tmp, 1);
    			tmp = ArrayUtils.add(tmp, tmp2[0]);
    			tmp = ArrayUtils.add(tmp, tmp2[1]);
    		}
    		
    		if(tmp.length <= 2 && tmp.length >= 3){
    			throw new RuntimeException("Invalid config at " + in + " in no-harvest");
    		}
    		String name = tmp[0];
    		int meta = Integer.valueOf(tmp[1]);
    		
    		if(harvest.containsKey(name)){
    			BlockEntry entry = harvest.get(name);
    			entry.addMeta(meta);
    			harvest.put(name, entry);
    		} else {
    			BlockEntry entry = new BlockEntry(name,meta);
    			harvest.put(name, entry);
    		}
    		
    		if(tmp.length==3){
    			BlockEntry entry = harvest.get(name);
    			entry.addDim(Integer.valueOf(tmp[2]));
    			harvest.put(name, entry);
    		}
    		
    	}
    	for(String in: Config.dontPlace){
    		String[] tmp = in.split("\\|");
    		String[] tmp2 = tmp[1].split("\\$");
    		if(tmp2.length == 2){
    			tmp = ArrayUtils.remove(tmp, 1);
    			tmp = ArrayUtils.add(tmp, tmp2[0]);
    			tmp = ArrayUtils.add(tmp, tmp2[1]);
    		}
    		
    		if(tmp.length <= 2 && tmp.length >= 3){
    			throw new RuntimeException("Invalid config at " + in + " in no-placement");
    		}
    		String name = tmp[0];
    		int meta = Integer.valueOf(tmp[1]);
    		
    		if(place.containsKey(name)){
    			BlockEntry entry = place.get(name);
    			entry.addMeta(meta);
    			place.put(name, entry);
    		} else {
    			BlockEntry entry = new BlockEntry(name,meta);
    			place.put(name, entry);
    		}
    		
    		if(tmp.length==3){
    			BlockEntry entry = place.get(name);
    			entry.addDim(Integer.valueOf(tmp[2]));
    			place.put(name, entry);
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
        	if(!harvest.get(name).getDim().isEmpty() && harvest.get(name).getMeta().contains(meta)){
        		if(harvest.get(name).getDim().contains(event.getWorld().provider.getDimension())){
        			event.setCanceled(true);
        		}
        	}
        	if(harvest.get(name).getMeta().contains(meta) && harvest.get(name).getDim().isEmpty()) {
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
        	if(!place.get(name).getDim().isEmpty() && place.get(name).getMeta().contains(meta)){
        		if(place.get(name).getDim().contains(event.getWorld().provider.getDimension())){
        			event.setCanceled(true);
        		}
        	}
        	
        	if(place.get(name).getMeta().contains(meta) && place.get(name).getDim().isEmpty()) {
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
        	if(!interact.get(name).getDim().isEmpty() && interact.get(name).getMeta().contains(meta)){
        		if(interact.get(name).getDim().contains(event.getWorld().provider.getDimension())){
        			event.setCanceled(true);
        		}
        	}
        	
        	if(interact.get(name).getMeta().contains(meta) && interact.get(name).getDim().isEmpty()) {
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