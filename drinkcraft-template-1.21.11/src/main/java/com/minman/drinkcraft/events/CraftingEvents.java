package com.minman.drinkcraft.events;

import com.minman.drinkcraft.EventId;
import com.minman.drinkcraft.PlayerEventTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CraftingEvents {

    public static void onItemCraft(PlayerEntity player, ItemStack craftedItem){


        // Iron Axe event
        if (craftedItem.isOf(Items.IRON_PICKAXE) && PlayerEventTracker.shouldTrack(EventId.FIRST_IRON_PICK, player.getUuid())){
            player.sendMessage(PlayerEventTracker.trackEventWithPlayerMessage(EventId.FIRST_IRON_PICK, player.getUuid()), false);

        }

        // Eye of Ender event
        if (craftedItem.isOf(Items.ENDER_EYE)){
            player.sendMessage(PlayerEventTracker.trackEventForAllWithPlayerMessage(EventId.EYE_OF_ENDER), false);

        }

    }
}
