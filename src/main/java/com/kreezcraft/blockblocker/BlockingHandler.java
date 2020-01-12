package com.kreezcraft.blockblocker;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

public class BlockingHandler {
	
	private void debug(EntityPlayer player, String msg) {
		if (BlockConfig.debugMode)
			player.addChatMessage(new ChatComponentText(msg));
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent ev) {
		if (BlockConfig.dontInteract.equals(null))
			return;
		if (ev.action == Action.LEFT_CLICK_BLOCK || ev.action == Action.RIGHT_CLICK_BLOCK) {
			Block thing = ev.world.getBlock(ev.x, ev.y, ev.z);
			int id = Block.getIdFromBlock(thing);
			int m = thing.getDamageValue(ev.world, ev.x, ev.y, ev.z);
			String sid = id + ":" + m;

			debug(ev.entityPlayer, "Player Interaction: " + sid);

			for (String str : BlockConfig.dontInteract) {
				if (str.trim().equals(sid.trim())) {
					debug(ev.entityPlayer, "dontInteract Match: "+str.trim());
					ev.setCanceled(true);
					return;
				}
			}

		}
	}

	@SubscribeEvent
	public void onPlayerHarvest(BreakEvent ev) {
		if (BlockConfig.dontHarvest.equals(null))
			return;

		Block thing = ev.world.getBlock(ev.x, ev.y, ev.z);
		int id = Block.getIdFromBlock(thing);
		int m = thing.getDamageValue(ev.world, ev.x, ev.y, ev.z);
		String sid = id + ":" + m;

		debug(ev.getPlayer(), "Player Harvest: " + sid);

		for (String str : BlockConfig.dontHarvest) {
			if (str.trim().equals(sid.trim())) {
				debug(ev.getPlayer(), "dontHarvest Match: "+str.trim());
				ev.setCanceled(true);
				return;
			}
		}

	}

	@SubscribeEvent
	public void onPlayerPlace(PlaceEvent ev) {
		if (BlockConfig.dontPlace.equals(null))
			return;

		Block thing = ev.world.getBlock(ev.x, ev.y, ev.z);
		int id = Block.getIdFromBlock(thing);
		int m = thing.getDamageValue(ev.world, ev.x, ev.y, ev.z);
		String sid = id + ":" + m;

		debug(ev.player, "Player Placement: " + sid);

		for (String str : BlockConfig.dontPlace) {
			if (str.trim().equals(sid.trim())) {
				debug(ev.player, "dontPlace Match: "+str.trim());
				ev.setCanceled(true);
				return;
			}
		}

	}

}