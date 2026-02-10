package com.minman.drinkcraft.events;

import com.minman.drinkcraft.DrinkEventRegistry;
import com.minman.drinkcraft.EventId;
import com.minman.drinkcraft.PlayerEventTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class CraftingEvents {

    public static void onItemCraft(PlayerEntity player, ItemStack craftedItem){
        System.out.println("In onItemCraft");
        System.out.println(craftedItem.getItem());
        System.out.println(PlayerEventTracker.shouldTrack(EventId.FIRST_IRON_PICK));

        // Iron Axe event
        if (craftedItem.isOf(Items.IRON_PICKAXE) && PlayerEventTracker.shouldTrack(EventId.FIRST_IRON_PICK)){
            PlayerEventTracker.trackEvent(EventId.FIRST_IRON_PICK);
            player.sendMessage(Text.literal(DrinkEventRegistry.getEvent(EventId.FIRST_IRON_PICK).displayName()
                    + ". Player Take " + DrinkEventRegistry.getEvent(EventId.FIRST_IRON_PICK).sips() + " Sips"), false);

        }
    }
}
