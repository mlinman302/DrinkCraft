package com.minman.drinkcraft.events;

import com.minman.drinkcraft.PlayerEventTracker;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.network.ServerPlayerEntity;

public class GrantAdvancementEvent {
    public static void logAdvancement(ServerPlayerEntity player, AdvancementEntry advancement) {

        if (PlayerEventTracker.shouldTrackAdvancement(advancement.id().toString(), player.getUuid())){
            player.sendMessage(PlayerEventTracker.trackAdvancementEventWithPlayerMessage(advancement.id().toString(), player.getUuid()));
        };
    }
}
