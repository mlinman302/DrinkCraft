package com.minman.drinkcraft;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class DrinkEventRegistry {
    private static final Map<String, DrinkEvent> allEvents = new HashMap<>();
    private static final Gson GSON = new Gson();

    public static void registerCustomEvents() {
            // Block breaking events
            registerEvent(new DrinkEvent(
                    EventIds.FIRST_WOOD_BREAK,
                    "First Wood Broken",
                    1,
                    2,
                    false
            ));

            // Death tracking
            registerEvent(new DrinkEvent(
                    EventIds.PLAYER_DEATH,
                    "Player Deaths",
                    -1,
                    2,
                    false
            ));

            // Kill tracking
            registerEvent(new DrinkEvent(
                    EventIds.PLAYER_KILL,
                    "Player Killed",
                    -1,
                    2,
                    false
            ));

            // Equipment events
            registerEvent(new DrinkEvent(
                    EventIds.FULL_ARMOR,
                    "Full Armor Equipped",
                    1,
                    2,
                    false
            ));


            // Eye of Ender
            registerEvent(new DrinkEvent(
                    EventIds.ENDER_EYE,
                    "Crafted First Eye of Ender",
                    1,
                    2,
                    true
            ));

            // All advancements
            registerEvent(new DrinkEvent(
                    EventIds.ALL_ADVANCEMENTS,
                    "Advancement",
                    -1,
                    2,
                    false
            ));

            // random block breaking event
            registerEvent(new DrinkEvent(
                    EventIds.RANDOM_STONE_BREAK,
                    "There's Beer in the Stone",
                    -1,
                    2,
                    false
            ));
        }

    public static DrinkEvent getEvent(String id){
        return allEvents.get(id);
    }

    public static Collection<DrinkEvent> getAllEvents(){
        return allEvents.values();
    }

    private static void registerEvent(DrinkEvent event) {
        allEvents.put(event.id(), event);
    }



}


