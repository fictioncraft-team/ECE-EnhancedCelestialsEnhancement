package fictioncraft.wintersteve25.ece.common.config.entity;

import com.google.gson.*;
import fictioncraft.wintersteve25.ece.EnhancedCelestialsEnhancement;
import fictioncraft.wintersteve25.ece.common.config.crops.JsonCropConfig;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.ArrayList;

public class JsonConfig {

    public static JsonBuilder blood;
    public static JsonBuilder harvest;

    public static void createJsonConfig() {
        PrintWriter writer;
        File file = new File(JsonCropConfig.directory.getPath() + File.separator + "ece_config.json");

        if (!file.exists()) {
            try {
                writer = new PrintWriter(JsonCropConfig.directory.getPath() + File.separator + "ece_config.json");
            } catch (Exception e) {
                EnhancedCelestialsEnhancement.LOGGER.log(Level.ERROR, "Error writing ece_config.json");
                e.printStackTrace();
                return;
            }

            ArrayList<JsonBuilder.JsonEntityProperty> blood = new ArrayList<>();
            ArrayList<JsonBuilder.JsonEntityProperty> harvest = new ArrayList<>();

            JsonBuilder exampleBuild = new JsonBuilder(blood, harvest);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.print(gson.toJson(exampleBuild));
            writer.close();

            EnhancedCelestialsEnhancement.LOGGER.log(Level.INFO, "Written ece_config.json");
        }
    }

    public static void printExample() {
        PrintWriter writer;

        try {
            writer = new PrintWriter(JsonCropConfig.directory.getPath() + File.separator + "ece_config_example.json");
        } catch (Exception e) {
            EnhancedCelestialsEnhancement.LOGGER.log(Level.ERROR, "Error writing ece_config_example.json");
            e.printStackTrace();
            return;
        }
        ArrayList<JsonBuilder.JsonItemStackProperty> drops = new ArrayList<>();
        drops.add(new JsonBuilder.JsonItemStackProperty("minecraft:iron_ingot", 1, 3, 30));

        ArrayList<JsonBuilder.JsonEntityProperty> blood = new ArrayList<>();
        blood.add(new JsonBuilder.JsonEntityProperty("minecraft:cow", drops, true, false, false, 10, false));

        ArrayList<JsonBuilder.JsonEntityProperty> harvest = new ArrayList<>();
        harvest.add(new JsonBuilder.JsonEntityProperty("minecraft:slime", drops, true, false, true, 5, true));

        JsonBuilder exampleBuild = new JsonBuilder(blood, harvest);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        writer.print(gson.toJson(exampleBuild));
        writer.close();

        EnhancedCelestialsEnhancement.LOGGER.log(Level.INFO, "Written ece_config_example.json");
    }

    public static void read() {
        File file = new File(JsonCropConfig.directory.getPath() + File.separator + "ece_config.json");
        if (!file.exists()) {
            EnhancedCelestialsEnhancement.LOGGER.log(Level.ERROR, "ECE Config json file not found! Creating a new one..");
            createJsonConfig();
        } else {
            try {
                EnhancedCelestialsEnhancement.LOGGER.info("Attempting to read ece_config.json file...");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonBuilder builder = gson.fromJson(new FileReader(file), JsonBuilder.class);
                if (builder != null) {
                    if (!builder.getBlood().isEmpty()) {
                       blood = builder;
                    }

                    if (!builder.getHarvest().isEmpty()) {
                        harvest = builder;
                    }
                }
            } catch (FileNotFoundException e) {
                EnhancedCelestialsEnhancement.LOGGER.log(Level.ERROR, "ECE Config json file not found! Creating a new one..");
                e.printStackTrace();
                createJsonConfig();
            }
        }
    }
}
