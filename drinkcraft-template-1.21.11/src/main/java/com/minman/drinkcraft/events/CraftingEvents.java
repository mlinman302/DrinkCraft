package com.minman.drinkcraft.events;

import com.minman.drinkcraft.EventIds;
import com.minman.drinkcraft.PlayerEventTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CraftingEvents {

    public static void onItemCraft(PlayerEntity player, ItemStack craftedItem){

        // Creating an eye of ender
        if (craftedItem.isOf(Items.ENDER_EYE)){
            player.sendMessage(PlayerEventTracker.trackEventForAllWithPlayerMessage(EventIds.ENDER_EYE), false);

        }

    }
}
