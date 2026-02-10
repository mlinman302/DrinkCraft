package com.minman.drinkcraft.mixin;

import com.minman.drinkcraft.events.ArmorEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class onEquipStackMixin {

    @Inject(
            method = "onEquipStack",
            at = @At("TAIL")
    )
    public void onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci){
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof ServerPlayerEntity player){
            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR){
                ArmorEvents.trackArmorChange(player, slot, oldStack, newStack);
            }
        }

    }
}
