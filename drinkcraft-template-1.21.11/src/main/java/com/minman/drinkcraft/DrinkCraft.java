package com.minman.drinkcraft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DrinkCraft implements ModInitializer {
	public static final String MOD_ID = "drinkcraft";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

//		// Pass new players on the server to PlayerEventTracker to add new instance to players list
		ServerPlayerEvents.JOIN.register(PlayerEventTracker::registerPlayer);

		// Set up custom events
		DrinkEventRegistry.registerCustomEvents();



		LOGGER.info("Setup player event tracking logic.");


		// Player death (tracked at death)
		ServerLivingEntityEvents.AFTER_DEATH.register((deadEntity, damageSource) -> {
			if (deadEntity instanceof ServerPlayerEntity player){
				player.sendMessage(PlayerEventTracker.trackEventWithPlayerMessage(EventIds.PLAYER_DEATH, player.getUuid()), false);
			}
		});


		// Player kill
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity, damageSource) -> {
			// if a player on the server was killed by another player on the server
			// pattern matches entity to player
			if (killedEntity instanceof ServerPlayerEntity && entity instanceof ServerPlayerEntity player){
				player.sendMessage(PlayerEventTracker.trackEventWithPlayerMessage(EventIds.PLAYER_KILL, player.getUuid()), false);
			}
		});


		// Block breaking event: Handled in Fabric callback
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			// First wood break
			if(state.isIn(BlockTags.LOGS) && PlayerEventTracker.shouldTrack(EventIds.FIRST_WOOD_BREAK, player.getUuid())){
				// add to registry and push message
				player.sendMessage(PlayerEventTracker.trackEventWithPlayerMessage(EventIds.FIRST_WOOD_BREAK, player.getUuid()), false);
			}
		});
	}
}