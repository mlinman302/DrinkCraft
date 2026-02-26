package com.minman.drinkcraft.events;

import com.minman.drinkcraft.PlayerEventsTracker;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.network.ServerPlayerEntity;

public class GrantAdvancementEvent {
    public static void logAdvancement(ServerPlayerEntity player, AdvancementEntry advancement) {

        if (PlayerEventsTracker.shouldTrackAdvancement(advancement, player.getUuid())){
            PlayerEventsTracker.trackAdvancementWithPayload(advancement, player);
        };
    }
}
