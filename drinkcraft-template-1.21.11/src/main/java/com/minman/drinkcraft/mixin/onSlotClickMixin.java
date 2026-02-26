package com.minman.drinkcraft.mixin;


import com.minman.drinkcraft.DrinkCraft;
import com.minman.drinkcraft.events.CraftingEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class onSlotClickMixin {


    @Inject(
            method = "onSlotClick",
            at = @At("HEAD")
    )
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci){
        ScreenHandler handler = (ScreenHandler) (Object) this;


        // If we are inside a crafting screen
        if (handler instanceof CraftingScreenHandler || handler instanceof PlayerScreenHandler){
            DrinkCraft.LOGGER.info("Inside crafting screen");

            // check slot 0 (where the result of the crafting goes) for crafted item
            if (slotIndex == 0 && (actionType == SlotActionType.PICKUP || actionType == SlotActionType.QUICK_MOVE)){
                DrinkCraft.LOGGER.info("Clicking on slot 0");
                ItemStack craftedItem = handler.getSlot(0).getStack();

                // send the item to our crafting event callback
                if(!craftedItem.isEmpty() && !player.getEntityWorld().isClient()){
                    DrinkCraft.LOGGER.info("sending to callback");
                    CraftingEvents.onItemCraft(player, craftedItem);
                }
            }
        }
    }
}