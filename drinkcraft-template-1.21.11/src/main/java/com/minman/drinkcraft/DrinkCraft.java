package com.minman.drinkcraft;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
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

		LOGGER.info("Hello World");


		// Event tracking set up
		DrinkEventRegistry.register();


		// Player death (tracked after respawn)
		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, isAlive) -> {
			if (PlayerEventTracker.shouldTrack(EventId.PLAYER_DEATH)){
				PlayerEventTracker.trackEvent(EventId.PLAYER_DEATH);
				newPlayer.sendMessage(Text.literal(DrinkEventRegistry.getEvent(EventId.PLAYER_DEATH).displayName()
						+ ". Player Take " + DrinkEventRegistry.getEvent(EventId.PLAYER_DEATH).sips() + " Sips"), false);
			}
		});


		// Block breaking event: Handled in Fabric callback
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {

			// First wood break
			if(state.isIn(BlockTags.LOGS) && PlayerEventTracker.shouldTrack(EventId.FIRST_WOOD_BREAK)){
				// add to registry

				PlayerEventTracker.trackEvent(EventId.FIRST_WOOD_BREAK);
				player.sendMessage(Text.literal(DrinkEventRegistry.getEvent(EventId.FIRST_WOOD_BREAK).displayName()
						+ ". Player Take " + DrinkEventRegistry.getEvent(EventId.FIRST_WOOD_BREAK).sips() + " Sips"), false);
			}
		});









	}
}