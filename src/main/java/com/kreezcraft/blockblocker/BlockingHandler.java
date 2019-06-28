package com.kreezcraft.blockblocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockingHandler {
	
	private static Map<String,List<Integer>> harvest = new HashMap<String,List<Integer>>();
	private static Map<String,List<Integer>> place = new HashMap<String,List<Integer>>();
	private static Map<String,List<Integer>> interact = new HashMap<String,List<Integer>>();

	public static void load(WorldEvent.Load event) {
		for(String string : Config.GENERAL.dontHarvest.get()){
			String[] tmp = string.split("\\$");
			
			switch(tmp.length){
			case 1:
				if(!harvest.containsKey(tmp[0])){
					harvest.put(tmp[0], new ArrayList<Integer>());
				}
				break;
			case 2:
				if(harvest.containsKey(tmp[0])){
					List<Integer> list = harvest.get(tmp[0]);
					list.add(Integer.valueOf(tmp[1]));
					harvest.put(tmp[0], list);
				} else {
					List<Integer> list = new ArrayList<Integer>();
					list.add(Integer.valueOf(tmp[1]));
					harvest.put(tmp[0], list);
				}
				break;
			default:
				throw new RuntimeException("Invalid config at " + string + " in no-harvest");
			}
		}
		
		for(String string : Config.GENERAL.dontPlace.get()){
			String[] tmp = string.split("\\$");
			
			switch(tmp.length){
			case 1:
				if(!place.containsKey(tmp[0])){
					place.put(tmp[0], new ArrayList<Integer>());
				}
				break;
			case 2:
				if(place.containsKey(tmp[0])){
					List<Integer> list = place.get(tmp[0]);
					list.add(Integer.valueOf(tmp[1]));
					place.put(tmp[0], list);
				} else {
					List<Integer> list = new ArrayList<Integer>();
					list.add(Integer.valueOf(tmp[1]));
					place.put(tmp[0], list);
				}
				break;
			default:
				throw new RuntimeException("Invalid config at " + string + " in no-place");
			}
		}
		
		for(String string : Config.GENERAL.dontInteract.get()){
			String[] tmp = string.split("\\$");
			
			switch(tmp.length){
			case 1:
				if(!interact.containsKey(tmp[0])){
					interact.put(tmp[0], new ArrayList<Integer>());
				}
				break;
			case 2:
				if(interact.containsKey(tmp[0])){
					List<Integer> list = interact.get(tmp[0]);
					list.add(Integer.valueOf(tmp[1]));
					interact.put(tmp[0], list);
				} else {
					List<Integer> list = new ArrayList<Integer>();
					list.add(Integer.valueOf(tmp[1]));
					interact.put(tmp[0], list);
				}
				break;
			default:
				throw new RuntimeException("Invalid config at " + string + " in no-interact");
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
        
        if (harvest.containsKey(name)) {
        	if(!harvest.get(name).isEmpty()){
        		if(harvest.get(name).contains(event.getWorld().getDimension().getType().getId())){
        			event.setCanceled(true);
        		}
        	} else {
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
    public static BlockEvent.EntityPlaceEvent snailRider(BlockEvent.EntityPlaceEvent event) {
        Block theBlock = event.getState().getBlock();
        String name = theBlock.getRegistryName().toString();
        
        if (place.containsKey(name)) {
        	if(!place.get(name).isEmpty()){
        		if(place.get(name).contains(event.getWorld().getDimension().getType().getId())){
        			event.setCanceled(true);
        		}
        	} else {
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
        
        if (interact.containsKey(name)) {
        	if(!interact.get(name).isEmpty()){
        		if(interact.get(name).contains(event.getWorld().getDimension().getType().getId())){
        			event.setCanceled(true);
        		}
        	} else {
        		event.setCanceled(true);
        	}
        }
        return event;
    }
}