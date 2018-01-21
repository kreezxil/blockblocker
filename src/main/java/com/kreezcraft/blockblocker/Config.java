package com.kreezcraft.blockblocker;

import java.lang.reflect.Array;
import java.util.List;

import org.apache.logging.log4j.Level;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class Config {

    public static Configuration cfg = ServerProxy.config;

    private static final String CATEGORY_PLACEMENT = "no-placement";
    private static final String CATEGORY_HARVEST = "no-harvest";
    
    public static String[] dontPlace;
    public static String[] dontHarvest;
    
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
        cfg.addCustomCategoryComment(CATEGORY_PLACEMENT, "Prevent these BLOCKS from placing");
        
        dontPlace = cfg.getStringList("dontPlace", CATEGORY_PLACEMENT,  new String[] {"minecraft:bedrock","minecraft:air"}, "BLOCK id's in this list will not be placeable"); 
        
        cfg.addCustomCategoryComment(CATEGORY_HARVEST, "Prevent these BLOCKS from be harvested");
        dontHarvest = cfg.getStringList("dontHarvest", CATEGORY_HARVEST,  new String[] {"minecraft:bedrock","minecraft:air"}, "BLOCK id's in this list will not be harvestable"); 
        
    }

}