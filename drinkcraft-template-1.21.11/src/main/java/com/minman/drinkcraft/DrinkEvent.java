package com.minman.drinkcraft;


import java.util.Objects;

public record DrinkEvent(String id, String displayName, int maxOccurrences, int sips, boolean forAll) {

}

