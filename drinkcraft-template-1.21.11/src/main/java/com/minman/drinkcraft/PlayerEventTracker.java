package com.minman.drinkcraft;

import java.util.HashMap;
import java.util.Map;

public class PlayerEventTracker {
    private static final Map<EventId, Integer> eventCounts = new HashMap<>();
    private static int totalSips = 0;


    public static void trackEvent(EventId id){
        DrinkEvent event = DrinkEventRegistry.getEvent(id);

            if (event == null) {
                throw new RuntimeException("Drink event not found in registry.");
            }
            int currentCount = eventCounts.getOrDefault(id, 0);

            eventCounts.put(id, currentCount + 1);
            totalSips += event.sips();
    };

    public static boolean shouldTrack(EventId id){
        int numOccurences = eventCounts.getOrDefault(id, 0);
        int maxOccurences = DrinkEventRegistry.getEvent(id).maxOccurrences();
        return  numOccurences < maxOccurences || maxOccurences == -1;
    }


    public int getTotalSips(){
        return totalSips;
    }
}
