package com.kreezcraft.blockblocker;

import java.util.List;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class BlockConfig {

    private static ForgeConfigSpec.ConfigValue<List<? extends String>> v_dontInteract;
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> v_dontHarvest;
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> v_dontPlace;

    public static List<String> dontInteract;
    public static List<String> dontHarvest;
    public static List<String> dontPlace;

    public static void init() {
        Pair<Loader,ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(Loader::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (ForgeConfigSpec)specPair.getRight());
    }

    public static void load() {
        dontInteract = (List<String>)v_dontInteract.get();
        dontHarvest = (List<String>)v_dontHarvest.get();
        dontPlace = (List<String>)v_dontPlace.get();
    }

    static class Loader
    {
        public Loader(ForgeConfigSpec.Builder builder) {

            builder.push("General");
            v_dontInteract = builder
                    .comment("Prevent these BLOCKS from being interacted with entirely (Only works if configured on client as well as server).")
                    .translation("bbconfig.interact")
                    .defineList("don't interact",(List<String>)ImmutableList.of("minecraft:bedrock","minecraft:air"), a -> true);
            v_dontHarvest = builder
                    .comment("BLOCK id's in this list will not be harvestable.")
                    .translation("bbconfig.harvest")
                    .defineList("don't harvest",(List<String>)ImmutableList.of("minecraft:bedrock","minecraft:air"), a -> true);
            v_dontPlace = builder
                    .comment("BLOCK id's in this list will not be placeable and\nwill not spawn in the world nor return to the player.")
                    .translation("bbconfig.placement")
                    .defineList("don't place",(List<String>)ImmutableList.of("minecraft:bedrock","minecraft:air"), a -> true);
            builder.pop();

        }
    }
}