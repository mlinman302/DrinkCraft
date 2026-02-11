package com.minman.drinkcraft;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.*;

public class PlayerEventTracker {
    private Map<String, Integer> eventCounts = new HashMap<>();
    private Map<String, Integer> advancementEvents = new HashMap<>();
    private int totalSips = 0;
    private final String name;

    private static final Map<UUID, PlayerEventTracker> trackers = new HashMap<>();


    public PlayerEventTracker(Map<String, Integer> eventCounts,
                              Map<String, Integer> advancementEvents,
                              int totalSips,
                              String name){
        this.eventCounts = eventCounts;
        this.advancementEvents = advancementEvents;
        this.totalSips = totalSips;
        this.name = name;
    }

    public PlayerEventTracker(String username){
        this.name = username;
    }

    // registers a new player if they aren't registered on the session yet
    public static void registerPlayer(ServerPlayerEntity player){
        trackers.putIfAbsent(player.getUuid(), new PlayerEventTracker(player.getStringifiedName()));
    }

    // tracks an event to a specific player
    public static PlayerEventTracker trackEvent(String id, UUID uuid){
        DrinkEvent event = DrinkEventRegistry.getEvent(id);

            if (event == null) {
                throw new RuntimeException("Drink event not found in registry.");
            }

            PlayerEventTracker tracker = getPlayerTracker(uuid);

            int currentCount = tracker.eventCounts.getOrDefault(id, 0);
            tracker.eventCounts.put(id, currentCount + 1);
            tracker.totalSips += event.sips();

            return tracker;
    };

    public static Text trackEventWithPlayerMessage(String id, UUID uuid){
        PlayerEventTracker tracker = trackEvent(id, uuid);
        return Text.literal(DrinkEventRegistry.getEvent(id).displayName()
                + ". " + tracker.name + " Takes " + DrinkEventRegistry.getEvent(id).sips() + " Sips");
    };

    public static void trackEventForAll(String id){
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

    public static Text trackEventForAllWithPlayerMessage(String id){
        trackEventForAll(id);
        return Text.literal(DrinkEventRegistry.getEvent(id).displayName()
                + ". All Players Take " + DrinkEventRegistry.getEvent(id).sips() + " Sips");

    };

    public static boolean shouldTrack(String id, UUID uuid){
        PlayerEventTracker tracker = getPlayerTracker(uuid);
        int numOccurrences = tracker.eventCounts.getOrDefault(id, 0);
        int maxOccurrences = DrinkEventRegistry.getEvent(id).maxOccurrences();
        return  numOccurrences < maxOccurrences || maxOccurrences == -1;
    };

    public static PlayerEventTracker trackAdvancementEvent(String id, UUID uuid) {
        PlayerEventTracker tracker = getPlayerTracker(uuid);
        tracker.advancementEvents.putIfAbsent(id, 2);
        tracker.totalSips += DrinkEventRegistry.getEvent(EventIds.ALL_ADVANCEMENTS).sips();
        return tracker;
    }

    public static Text trackAdvancementEventWithPlayerMessage(String id, UUID uuid){
        PlayerEventTracker tracker = trackAdvancementEvent(id, uuid);
        return Text.literal(id + tracker.name + " Take " +
                DrinkEventRegistry.getEvent(EventIds.ALL_ADVANCEMENTS).sips() + " Sips");
    }

    public static boolean shouldTrackAdvancement(String id, UUID uuid){
        String[] splitId = id.split("[:/]");
        return trackableAdvancement(splitId) && playerDoesntHaveAdvancement(id, uuid);
    }

    public static Collection<UUID> getAllUUIDs(){
        return trackers.keySet();
    }

    public static int getPlayerTotalSips(UUID uuid){
        PlayerEventTracker tracker = getPlayerTracker(uuid);
        return tracker.totalSips;
    };

    public Map<String, Integer> getEventCounts(){
        return this.eventCounts;
    }
    public Map<String, Integer> getAdvancementEvents(){
        return this.advancementEvents;
    }

    public int getTotalSips(){
        return this.totalSips;
    }

    public String getName(){
        return this.name;
    }

    public static final Codec<PlayerEventTracker> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                            Codec.unboundedMap(Codec.STRING, Codec.INT)
                                    .fieldOf("eventCounts")
                                    .forGetter(PlayerEventTracker::getEventCounts),
                            Codec.unboundedMap(Codec.STRING, Codec.INT)
                                    .fieldOf("advancementEvents")
                                    .forGetter(PlayerEventTracker::getAdvancementEvents),
                            Codec.INT
                                    .fieldOf("totalSips")
                                    .forGetter(PlayerEventTracker::getTotalSips),
                            Codec.STRING
                                    .fieldOf("playerName")
                                    .forGetter(PlayerEventTracker::getName))
                    .apply(instance, PlayerEventTracker::new)
            );

    private static PlayerEventTracker getPlayerTracker(UUID id){
        return trackers.get(id);
    };

    private static boolean trackableAdvancement(String[] splitAdvancementId){
        String branchName = splitAdvancementId[1];
        return splitAdvancementId[0].equals("minecraft") &&
                (branchName.equals("story") || branchName.equals("nether") || branchName.equals("end"));
    }

    private static boolean playerDoesntHaveAdvancement(String id, UUID uuid){
        PlayerEventTracker tracker = getPlayerTracker(uuid);
        return (tracker.advancementEvents.getOrDefault(id, 0) == 0);

    }
}
