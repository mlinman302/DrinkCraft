package com.minman.drinkcraft;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class DrinkEventRegistry {
    private static final Map<EventId, DrinkEvent> allEvents = new HashMap<>();
    private static final Gson GSON = new Gson();

    public static void register() {
            // Block breaking events
            registerEvent(new DrinkEvent(
                    EventId.FIRST_WOOD_BREAK,
                    "First Wood Broken",
                    1,
                    3
            ));

            // Death tracking
            registerEvent(new DrinkEvent(
                    EventId.PLAYER_DEATH,
                    "Player Deaths",
                    -1,
                    3
            ));

            // Crafting events
            registerEvent(new DrinkEvent(
                    EventId.FIRST_IRON_PICK,
                    "First Iron Pickaxe Crafted",
                    1,
                    1
            ));

            // Equipment events
            registerEvent(new DrinkEvent(
                    EventId.FULL_ARMOR,
                    "Full Armor Equipped",
                    1,
                    3
            ));

            // Equipment events
            registerEvent(new DrinkEvent(
                    EventId.ARMOR,
                    "One Armor Piece Equipped",
                    1,
                    2
        ));
        }

    // TODO: complete method for ability to load from JSON
    public static void registerFromList() {
        try (InputStream stream = DrinkEventRegistry.class.getResourceAsStream("eventList.json")) {

            if (stream == null){
                throw new FileNotFoundException("json file not found in data");
            }

            try (Reader in = new InputStreamReader(stream)){
                DrinkEventsConfig config = GSON.fromJson(in, DrinkEventsConfig.class);

                for (DrinkEventsConfig.EventData event : config.getEvents()){
                    DrinkEvent e = new DrinkEvent(
                            event.id,
                            event.displayName,
                            event.maxOccurrences,
                            event.sips
                    );
                    DrinkEventRegistry.registerEvent(e);
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("JSON File not found: " + e);
        } catch (Exception e) {
            System.out.println("Other error: " + e);
        }


    }

    public static DrinkEvent getEvent(EventId id){
        return allEvents.get(id);
    }

    public static Collection<DrinkEvent> getAllEvents(){
        return allEvents.values();
    }



    private static void registerEvent(DrinkEvent event) {
        allEvents.put(event.id(), event);
    }



}


