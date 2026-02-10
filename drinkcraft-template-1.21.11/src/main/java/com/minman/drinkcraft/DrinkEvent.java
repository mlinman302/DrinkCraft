package com.minman.drinkcraft;


public record DrinkEvent(EventId id, String displayName, int maxOccurrences, int sips, boolean forAll) {

}

