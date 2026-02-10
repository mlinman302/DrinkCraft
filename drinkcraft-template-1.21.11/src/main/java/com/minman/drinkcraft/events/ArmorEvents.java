package com.minman.drinkcraft.events;

import com.minman.drinkcraft.EventId;
import com.minman.drinkcraft.PlayerEventTracker;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ArmorEvents {
    public static void trackArmorChange(ServerPlayerEntity player, EquipmentSlot slot, ItemStack oldStack, ItemStack newStack) {
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);


        // if the full armor is equipped
        if(! (helmet.isEmpty() && chestplate.isEmpty() && leggings.isEmpty() && boots.isEmpty())){
            // if we can track the event
            if (PlayerEventTracker.shouldTrack(EventId.FULL_ARMOR, player.getUuid())){
                player.sendMessage(PlayerEventTracker.trackEventWithPlayerMessage(EventId.FULL_ARMOR, player.getUuid()));
            }

        }

    }
}
