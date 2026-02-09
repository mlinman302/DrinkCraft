package com.minman.drinkcraft;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.registry.tag.BlockTags;
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


		// Block break event handler
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {

			// First wood break
			if(state.isIn(BlockTags.LOGS) && PlayerEventTracker.shouldTrack(EventId.FIRST_WOOD_BREAK)){
				// add to registry

				PlayerEventTracker.trackEvent(EventId.FIRST_WOOD_BREAK);
				player.sendMessage(Text.literal(DrinkEventRegistry.getEvent(EventId.FIRST_WOOD_BREAK).displayName()
						+ ". Player Take " + DrinkEventRegistry.getEvent(EventId.FIRST_WOOD_BREAK).sips() + " Sips"), true);
			}
		});

		// Crafting tracking handler







	}
}