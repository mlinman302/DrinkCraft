package com.minman.drinkcraft;

import java.util.List;

public class DrinkEventsConfig {
    private List<EventData> events;

    public static class EventData {
        public String id;
        public String displayName;
        public int maxOccurrences;
        public int sips;
        public boolean forAll;
    }

    public List<EventData> getEvents(){
        return events;
    }
}
