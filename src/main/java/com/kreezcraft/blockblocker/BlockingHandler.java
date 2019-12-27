package com.kreezcraft.blockblocker;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

public class BlockingHandler {

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent ev) {
		if (BlockConfig.dontInteract.length <= 0)
			return;
		if (ev.action == Action.LEFT_CLICK_BLOCK || ev.action == Action.RIGHT_CLICK_BLOCK) {
			Block thing = ev.world.getBlock(ev.x, ev.y, ev.z);
			int id = Block.getIdFromBlock(thing);

			ev.entityPlayer.addChatMessage(new ChatComponentText("Player Interaction"));

			for (int i = 0; i <= BlockConfig.dontInteract.length; i++) {
				if (BlockConfig.dontInteract[i] == id) {
					ev.setCanceled(true);
					return;
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerHarvest(BreakEvent ev) {
		if (BlockConfig.dontHarvest.length <= 0)
			return;

		Block thing = ev.world.getBlock(ev.x, ev.y, ev.z);
		int id = Block.getIdFromBlock(thing);

		ev.getPlayer().addChatMessage(new ChatComponentText("Player Harvest"));

		for (int i = 0; i <= BlockConfig.dontHarvest.length; i++) {
			if (BlockConfig.dontHarvest[i] == id) {
				ev.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent
	public void onPlayerPlace(PlaceEvent ev) {
		if (BlockConfig.dontPlace.length <= 0)
			return;

		Block thing = ev.world.getBlock(ev.x, ev.y, ev.z);
		int id = Block.getIdFromBlock(thing);

		ev.player.addChatMessage(new ChatComponentText("Player Placement"));

		for (int i = 0; i <= BlockConfig.dontPlace.length; i++) {
			if (BlockConfig.dontPlace[i] == id) {
				ev.setCanceled(true);
				return;
			}
		}

	}

//	private static Map<String,List<Integer>> harvest = new HashMap<String,List<Integer>>();
//	private static Map<String,List<Integer>> place = new HashMap<String,List<Integer>>();
//	private static Map<String,List<Integer>> interact = new HashMap<String,List<Integer>>();
//
//	@SubscribeEvent
//	public static void load(WorldEvent.Load event) {
//		for(String string : BlockConfig.GENERAL.dontHarvest.get()){
//			String[] tmp = string.split("\\$");
//			
//			switch(tmp.length){
//			case 1:
//				if(!harvest.containsKey(tmp[0])){
//					harvest.put(tmp[0], new ArrayList<Integer>());
//				}
//				break;
//			case 2:
//				if(harvest.containsKey(tmp[0])){
//					List<Integer> list = harvest.get(tmp[0]);
//					list.add(Integer.valueOf(tmp[1]));
//					harvest.put(tmp[0], list);
//				} else {
//					List<Integer> list = new ArrayList<Integer>();
//					list.add(Integer.valueOf(tmp[1]));
//					harvest.put(tmp[0], list);
//				}
//				break;
//			default:
//				throw new RuntimeException("Invalid config at " + string + " in no-harvest");
//			}
//		}
//		
//		for(String string : BlockConfig.GENERAL.dontPlace.get()){
//			String[] tmp = string.split("\\$");
//			
//			switch(tmp.length){
//			case 1:
//				if(!place.containsKey(tmp[0])){
//					place.put(tmp[0], new ArrayList<Integer>());
//				}
//				break;
//			case 2:
//				if(place.containsKey(tmp[0])){
//					List<Integer> list = place.get(tmp[0]);
//					list.add(Integer.valueOf(tmp[1]));
//					place.put(tmp[0], list);
//				} else {
//					List<Integer> list = new ArrayList<Integer>();
//					list.add(Integer.valueOf(tmp[1]));
//					place.put(tmp[0], list);
//				}
//				break;
//			default:
//				throw new RuntimeException("Invalid config at " + string + " in no-place");
//			}
//		}
//		
//		for(String string : BlockConfig.GENERAL.dontInteract.get()){
//			String[] tmp = string.split("\\$");
//			
//			switch(tmp.length){
//			case 1:
//				if(!interact.containsKey(tmp[0])){
//					interact.put(tmp[0], new ArrayList<Integer>());
//				}
//				break;
//			case 2:
//				if(interact.containsKey(tmp[0])){
//					List<Integer> list = interact.get(tmp[0]);
//					list.add(Integer.valueOf(tmp[1]));
//					interact.put(tmp[0], list);
//				} else {
//					List<Integer> list = new ArrayList<Integer>();
//					list.add(Integer.valueOf(tmp[1]));
//					interact.put(tmp[0], list);
//				}
//				break;
//			default:
//				throw new RuntimeException("Invalid config at " + string + " in no-interact");
//			}
//		}
//	}
//	
//    /**
//     * Stops Breakage
//     */
//    @SubscribeEvent
//    public static BreakEvent thoseDarnBlocks(BreakEvent event) {
//    	
//        Block theBlock = event.getState().getBlock();
//        String name = theBlock.getRegistryName().toString();
//        
//        if (harvest.containsKey(name)) {
//        	if(!harvest.get(name).isEmpty()){
//        		if(harvest.get(name).contains(event.getWorld().getDimension().getType().getId())){
//        			event.setCanceled(true);
//        		}
//        	} else {
//        		event.setCanceled(true);
//        	}
//        }
//            
//        return event;
//    }
//
//    /**
//     * Stops placement
//     *
//     */
//    @SubscribeEvent
//    public static BlockEvent.EntityPlaceEvent snailRider(BlockEvent.EntityPlaceEvent event) {
//        Block theBlock = event.getState().getBlock();
//        String name = theBlock.getRegistryName().toString();
//        
//        if (place.containsKey(name)) {
//        	if(!place.get(name).isEmpty()){
//        		if(place.get(name).contains(event.getWorld().getDimension().getType().getId())){
//        			event.setCanceled(true);
//        		}
//        	} else {
//        		event.setCanceled(true);
//        	}
//        }
//        
//        return event;
//    }
//
//    /**
//     * Stops interactions
//     */
//
//    @SubscribeEvent
//    public static RightClickBlock foxFire(RightClickBlock event) {
//        BlockPos target = event.getPos();
//
//        if (event.getEntityPlayer().getPosition() == target) {
//            return event;
//        }
//        
//        Block theBlock = event.getWorld().getBlockState(target).getBlock();
//        String name = theBlock.getRegistryName().toString();
//        
//        if (interact.containsKey(name)) {
//        	if(!interact.get(name).isEmpty()){
//        		if(interact.get(name).contains(event.getWorld().getDimension().getType().getId())){
//        			event.setCanceled(true);
//        		}
//        	} else {
//        		event.setCanceled(true);
//        	}
//        }
//        return event;
//    }
}