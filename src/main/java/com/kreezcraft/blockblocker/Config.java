package com.kreezcraft.blockblocker;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {

    private static final String CATEGORY_PLACEMENT = "no-placement";
    private static final String CATEGORY_HARVEST = "no-harvest";
    private static final String CATEGORY_INTERACT = "no-interact";
    public static Configuration cfg = ServerProxy.config;
    public static String[] dontPlace;
    public static String[] dontHarvest;
    public static String[] dontInteract;

    public static void readConfig() {
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            BlockBlocker.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {

        cfg.addCustomCategoryComment(CATEGORY_PLACEMENT, "Prevent these BLOCKS from placing. Be sure to include metadata in the Block Ids. Format should be \"modid:block|metadata\" or \"modid:block|metadata$dimId\" if you want to include dimentions");
        dontPlace = cfg.getStringList("dontPlace", CATEGORY_PLACEMENT, new String[]{"minecraft:bedrock|0", "minecraft:air|0"}, "BLOCK id's in this list will not be placeable and\nwill not spawn in the world nor return to the player");

        cfg.addCustomCategoryComment(CATEGORY_HARVEST, "Prevent these BLOCKS from be harvested. Be sure to include metadata in the Block Ids. Format should be \"modid:block|metadata\" or \"modid:block|metadata$dimId\" if you want to include dimentions");
        dontHarvest = cfg.getStringList("dontHarvest", CATEGORY_HARVEST, new String[]{"minecraft:bedrock|0", "minecraft:air|0"}, "BLOCK id's in this list will not be harvestable");

        cfg.addCustomCategoryComment(CATEGORY_INTERACT, "Prevent these BLOCKS from being interacted with entirely (Only works if configured on client as well as server). Be sure to include metadata in the Block Ids. Format should be \"modid:block|metadata\" or \"modid:block|metadata$dimId\" if you want to include dimentions");
        dontInteract = cfg.getStringList("dontInteract", CATEGORY_INTERACT, new String[]{"minecraft:bedrock|0", "minecraft:air|0"}, "BLOCK id's in this list will not be right clickable");
    }

}