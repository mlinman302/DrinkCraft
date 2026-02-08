package com.minman.drinkcraft;

import com.mojang.text2speech.Narrator;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
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
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello World");

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {

			if(state.isIn(BlockTags.LOGS)){
				Narrator.getNarrator().say("Breaking Wood", true, 100);
			}
		});

	}
}