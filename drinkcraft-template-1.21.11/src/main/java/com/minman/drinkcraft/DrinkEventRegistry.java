package com.minman.drinkcraft;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import jdk.jfr.Event;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.MinecraftServer;

public class DrinkEventRegistry {
    private static final Map<EventId, DrinkEvent> allEvents = new HashMap<>();
    private static final Gson GSON = new Gson();

    public static void register() {
            // Block breaking events
            registerEvent(new DrinkEvent(
                    EventId.FIRST_WOOD_BREAK,
                    "First Wood Broken",
                    1,
                    2,
                    false
            ));

            // Death tracking
            registerEvent(new DrinkEvent(
                    EventId.PLAYER_DEATH,
                    "Player Deaths",
                    -1,
                    2,
                    false
            ));

            // Kill tracking

            registerEvent(new DrinkEvent(
                    EventId.PLAYER_KILL,
                    "Player Killed",
                    -1,
                    2,
                    false
            ));

            // Equipment events
            registerEvent(new DrinkEvent(
                    EventId.FULL_ARMOR,
                    "Full Armor Equipped",
                    1,
                    2,
                    false
            ));


            // Eye of Ender
            registerEvent(new DrinkEvent(
                    EventId.EYE_OF_ENDER,
                    "Crafted First Eye of Ender",
                    1,
                    2,
                    true
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
                            event.sips,
                            event.forAll
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

    public static void registerAdvancements(MinecraftServer server){
        for (AdvancementEntry advancement : server.getAdvancementLoader().getAdvancements()){
            String advancementId = advancement.id().toString();
            advancement.value().display().ifPresent(display -> {
                String title = display.getTitle().getString();
                DrinkEvent event = new DrinkEvent(
                        advancementId,
                        title,
                        1,
                        2,
                        false


                )
            });
        }

    }



    private static void registerEvent(DrinkEvent event) {
        allEvents.put(event.id(), event);
    }



}


