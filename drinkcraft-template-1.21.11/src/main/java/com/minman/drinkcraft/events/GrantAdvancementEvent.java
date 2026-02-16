package com.minman.drinkcraft.events;

import com.minman.drinkcraft.PlayerEventTracker;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.network.ServerPlayerEntity;

public class GrantAdvancementEvent {
    public static void logAdvancement(ServerPlayerEntity player, AdvancementEntry advancement) {

        if (PlayerEventTracker.shouldTrackAdvancement(advancement, player.getUuid())){
            PlayerEventTracker.trackAdvancementWithPayload(advancement, player);
        };
    }
}
