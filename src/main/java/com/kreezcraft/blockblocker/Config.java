package com.kreezcraft.blockblocker;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Config {

	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);

    public static class General {
        public final ConfigValue<List<? extends String>> dontInteract;
        public final ConfigValue<List<? extends String>> dontHarvest;
        public final ConfigValue<List<? extends String>> dontPlace;

        public General(ForgeConfigSpec.Builder builder) {
        	
        	List<String> defaultValues = Arrays.asList(new String[]{"minecraft:bedrock", "minecraft:air"});
        	
            builder.push("General");
            dontInteract = builder
                    .comment("Prevent these BLOCKS from being interacted with entirely (Only works if configured on client as well as server)")
                    .translation("bbconfig.interact")
                    .define("REE", defaultValues);
            dontHarvest = builder
            		.comment("BLOCK id's in this list will not be harvestable")
            		.translation("bbconfig.harvest")
            		.define("blacklist", defaultValues);
            dontPlace = builder
            		.comment("BLOCK id's in this list will not be placeable and\nwill not spawn in the world nor return to the player")
            		.translation("bbconfig.placement")
            		.define("randomBed", defaultValues);
            builder.pop();
        }
        
    }
    public static final ForgeConfigSpec spec = BUILDER.build();

}