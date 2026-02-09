package com.minman.drinkcraft;

import java.util.List;

public class DrinkEventsConfig {
    private List<EventData> events;

    public static class EventData {
        public EventId id;
        public String displayName;
        public int maxOccurrences;
        public int sips;
    }

    public List<EventData> getEvents(){
        return events;
    }
}
