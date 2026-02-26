package com.minman.drinkcraft;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.Map;


// events for a specific player
public class PlayerEvents {
    private final Map<String, Integer> eventCounts;
    private final Map<String, Integer> advancementEvents;
    private int totalSips;
    private final String name;

    // getters
    public Map<String, Integer> getEventCounts(){
        return this.eventCounts;
    }

    public Map<String, Integer> getAdvancementEvents(){
        return this.advancementEvents;
    }

    public Integer getTotalSips(){
        return this.totalSips;
    }

    public String getName(){
        return this.name;
    }

    public PlayerEvents(String name){
        this.totalSips = 0;
        this.name = name;
        this.eventCounts = new HashMap<>();
        this.advancementEvents = new HashMap<>();
    }



    public void addSips(int sips){
        this.totalSips += sips;
    }

    // constructor for saving
    public PlayerEvents(Map<String, Integer> eventCounts,
                              Map<String, Integer> advancementEvents,
                              int totalSips,
                              String name){
        this.eventCounts = new HashMap<>(eventCounts);
        this.advancementEvents = new HashMap<>(advancementEvents);
        this.totalSips = totalSips;
        this.name = name;
    }

    // should a player track this event?
    public boolean shouldTrack(String id){
        int numOccurrences = eventCounts.getOrDefault(id, 0);
        int maxOccurrences = DrinkEventRegistry.getEvent(id).maxOccurrences();
        return  numOccurrences < maxOccurrences || maxOccurrences == -1;
    };

    // should a player track this advancement?
    public boolean shouldTrackAdvancement(String advId){
        return advancementEvents.getOrDefault(advId, 0) == 0;
    }

    public static final Codec<PlayerEvents> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                            Codec.unboundedMap(Codec.STRING, Codec.INT)
                                    .fieldOf("eventCounts")
                                    .forGetter(PlayerEvents::getEventCounts),
                            Codec.unboundedMap(Codec.STRING, Codec.INT)
                                    .fieldOf("advancementEvents")
                                    .forGetter(PlayerEvents::getAdvancementEvents),
                            Codec.INT
                                    .fieldOf("totalSips")
                                    .forGetter(PlayerEvents::getTotalSips),
                            Codec.STRING
                                    .fieldOf("name")
                                    .forGetter(PlayerEvents::getName))
                    .apply(instance, PlayerEvents::new)
            );

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("PlayerEvents {\n");
        sb.append("  name: ").append(name).append("\n");
        sb.append("  totalSips: ").append(totalSips).append("\n");
        sb.append("  eventCounts: {\n");
        eventCounts.forEach((k, v) -> sb.append("    ").append(k).append(": ").append(v).append("\n"));
        sb.append("  }\n");
        sb.append("  advancementEvents: {\n");
        advancementEvents.forEach((k, v) -> sb.append("    ").append(k).append(": ").append(v).append("\n"));
        sb.append("  }\n");
        sb.append("}");
        return sb.toString();
    }

}
