package com.minman.drinkcraft.events;

import com.minman.drinkcraft.DrinkCraft;
import com.minman.drinkcraft.EventIds;
import com.minman.drinkcraft.PlayerEventsTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

public class CraftingEvents {

    public static void onItemCraft(PlayerEntity player, ItemStack craftedItem){

        if (player instanceof ServerPlayerEntity serverPlayer){
            // Creating an eye of ender
            if (craftedItem.isOf(Items.ENDER_EYE)){
                DrinkCraft.LOGGER.info("tracked ender eye craft");
                PlayerEventsTracker.trackEventForAllWithPayload(EventIds.ENDER_EYE, serverPlayer);

            }
        }


    }
}
