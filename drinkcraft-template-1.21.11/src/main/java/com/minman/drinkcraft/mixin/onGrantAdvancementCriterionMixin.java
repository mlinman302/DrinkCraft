package com.minman.drinkcraft.mixin;

import com.minman.drinkcraft.events.GrantAdvancementEvent;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public class onGrantAdvancementCriterionMixin {

    @Shadow
    private ServerPlayerEntity owner;

    @Inject(
            method = "grantCriterion",
            at = @At("TAIL")
    )public void grantCriterion(AdvancementEntry advancement, String criterionName, CallbackInfoReturnable<Boolean> cir){
        GrantAdvancementEvent.logAdvancement(this.owner, advancement);
        }
}
