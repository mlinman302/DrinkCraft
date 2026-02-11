package com.minman.drinkcraft;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerEventTracker {
    private final Map<EventId, Integer> eventCounts = new HashMap<>();
    private final Map<String, Integer> advancementEvents = new HashMap<>();
    private int totalSips = 0;
    private final String playerName;

    private static final Map<UUID, PlayerEventTracker> trackers = new HashMap<>();

    public PlayerEventTracker(String username){
        this.playerName = username;
    }


    // registers a new player if they aren't registered on the session yet
    public static void registerPlayer(ServerPlayerEntity player){
        trackers.putIfAbsent(player.getUuid(), new PlayerEventTracker(player.getStringifiedName()));
    }

    // tracks an event to a specific player
    public static void trackEvent(EventId id, UUID uuid){
        DrinkEvent event = DrinkEventRegistry.getEvent(id);

            if (event == null) {
                throw new RuntimeException("Drink event not found in registry.");
            }

            PlayerEventTracker tracker = getPlayerTracker(uuid);

            int currentCount = tracker.eventCounts.getOrDefault(id, 0);
            tracker.eventCounts.put(id, currentCount + 1);
            tracker.totalSips += event.sips();
    };

    public static Text trackEventWithPlayerMessage(EventId id, UUID uuid){
        trackEvent(id, uuid);
        PlayerEventTracker tracker = getPlayerTracker(uuid);
        return Text.literal(DrinkEventRegistry.getEvent(id).displayName()
                + ". " + tracker.playerName + " Takes " + DrinkEventRegistry.getEvent(id).sips() + " Sips");
    };

    public static void trackEventForAll(EventId id){
        DrinkEvent event = DrinkEventRegistry.getEvent(id);

        if (event == null) {
            throw new RuntimeException("Drink event not found in registry.");
        }

        trackers.keySet().forEach(uuid -> {
            if (shouldTrack(id, uuid)){
                trackEvent(id, uuid);
            }
        });

    };

    public static Text trackEventForAllWithPlayerMessage(EventId id){
        trackEventForAll(id);
        return Text.literal(DrinkEventRegistry.getEvent(id).displayName()
                + ". All Players Take " + DrinkEventRegistry.getEvent(id).sips() + " Sips");

    };

    public static int getPlayerTotalSips(UUID uuid){
        PlayerEventTracker tracker = getPlayerTracker(uuid);
        return tracker.totalSips;
    };

    public static boolean shouldTrack(EventId id, UUID uuid){
        PlayerEventTracker tracker = getPlayerTracker(uuid);
        int numOccurrences = tracker.eventCounts.getOrDefault(id, 0);
        int maxOccurrences = DrinkEventRegistry.getEvent(id).maxOccurrences();
        return  numOccurrences < maxOccurrences || maxOccurrences == -1;
    };

    public static void trackAdvancementEvent(ServerPlayerEntity player, AdvancementEntry advancement) {
        getPlayerTracker(player.getUuid()).advancementEvents.putIfAbsent(advancement.toString(), 2);
        System.out.print("My advancement: {}" + advancement.toString());

    }

    public static Collection<UUID> getAllUUIDs(){
        return trackers.keySet();
    }


    private static PlayerEventTracker getPlayerTracker(UUID id){
        return trackers.get(id);
    };




}
