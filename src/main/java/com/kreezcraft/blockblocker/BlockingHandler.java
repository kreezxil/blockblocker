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
		if (BlockConfig.dontInteract.equals(null))
			return;
		if (ev.action == Action.LEFT_CLICK_BLOCK || ev.action == Action.RIGHT_CLICK_BLOCK) {
			Block thing = ev.world.getBlock(ev.x, ev.y, ev.z);
			int id = Block.getIdFromBlock(thing);
			int m = thing.getDamageValue(ev.world, ev.x,  ev.y, ev.z);
			String sid = id+":"+m;

			//ev.entityPlayer.addChatMessage(new ChatComponentText("Player Interaction: "+sid));
			
			for(String str: BlockConfig.dontInteract ) {
				if(str.trim().contains(sid.trim())) {
					ev.setCanceled(true);
					return;
				}
			}

//			for (int i = 0; i < BlockConfig.dontInteract.length; i++) {
//				if (BlockConfig.dontInteract[i] == id) {
//					ev.setCanceled(true);
//					return;
//				}
//			}
		}
	}

	@SubscribeEvent
	public void onPlayerHarvest(BreakEvent ev) {
		if (BlockConfig.dontHarvest.equals(null))
			return;

		Block thing = ev.world.getBlock(ev.x, ev.y, ev.z);
		int id = Block.getIdFromBlock(thing);
		int m = thing.getDamageValue(ev.world, ev.x,  ev.y, ev.z);
		String sid = id+":"+m;

		//ev.getPlayer().addChatMessage(new ChatComponentText("Player Harvest: "+sid));

		for(String str: BlockConfig.dontHarvest ) {
			if(str.trim().contains(sid.trim())) {
				ev.setCanceled(true);
				return;
			}
		}

//		for (int i = 0; i < BlockConfig.dontHarvest.length; i++) {
//			if (BlockConfig.dontHarvest[i] == id) {
//				ev.setCanceled(true);
//				return;
//			}
//		}
	}

	@SubscribeEvent
	public void onPlayerPlace(PlaceEvent ev) {
		if (BlockConfig.dontPlace.equals(null))
			return;

		Block thing = ev.world.getBlock(ev.x, ev.y, ev.z);
		int id = Block.getIdFromBlock(thing);
		int m = thing.getDamageValue(ev.world, ev.x,  ev.y, ev.z);
		String sid = id+":"+m;

		//ev.player.addChatMessage(new ChatComponentText("Player Placement: "+sid));

		for(String str: BlockConfig.dontPlace ) {
			//ev.player.addChatMessage(new ChatComponentText("Comparing ["+sid+"] to ["+str+"]"));
			if(str.trim().contains(sid.trim())) {
				ev.setCanceled(true);
				return;
			}
		}

//		for (int i = 0; i < BlockConfig.dontPlace.length; i++) {
//			if (BlockConfig.dontPlace[i] == id) {
//				ev.setCanceled(true);
//				return;
//			}
//		}

	}

}