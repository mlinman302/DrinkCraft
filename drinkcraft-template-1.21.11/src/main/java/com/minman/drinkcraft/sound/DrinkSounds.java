package com.minman.drinkcraft.sound;

import com.minman.drinkcraft.DrinkCraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class DrinkSounds {

    public static final SoundEvent BEER_DRINK = register("beer_drink");

    private static SoundEvent register(String name){
        Identifier id = Identifier.of(DrinkCraft.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));

    }

    public static void registerSounds(){
        DrinkCraft.LOGGER.info("Registering sounds for: " + DrinkCraft.MOD_ID);
    }
}
