package com.minman.drinkcraft;

import com.minman.drinkcraft.sound.DrinkSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
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

		// when the server starts, create or load data
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			PlayersEventsState playersData = PlayersEventsState.getOrCreate(server);
			PlayerEventsTracker.updatePlayersState(playersData);
			LOGGER.info(PlayerEventsTracker.getPlayersState().toString());
		});


		// Pass new players on the server to PlayerEventsTracker to add new instance to players list
		ServerPlayerEvents.JOIN.register(PlayerEventsTracker::registerPlayer);

		// Set up custom events
		DrinkEventRegistry.registerCustomEvents();

		// Register sounds
		DrinkSounds.registerSounds();

		// Register payload information for server -> client comms
		DrinkEventPayload.registerPayload();



		// Player death (tracked at death)
		ServerLivingEntityEvents.AFTER_DEATH.register((deadEntity, damageSource) -> {
			if (deadEntity instanceof ServerPlayerEntity player){
				PlayerEventsTracker.trackEventWithPayload(EventIds.PLAYER_DEATH, player);
			}
		});


		// Player kill
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity, damageSource) -> {
			// if a player on the server was killed by another player on the server
			// pattern matches entity to player
			if (killedEntity instanceof ServerPlayerEntity && entity instanceof ServerPlayerEntity player){
				PlayerEventsTracker.trackEventWithPayload(EventIds.PLAYER_KILL, player);
			}
		});


		// Player break block
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if (player instanceof ServerPlayerEntity serverPlayer) {

				// First wood break
				if (state.isIn(BlockTags.LOGS) && PlayerEventsTracker.shouldTrack(EventIds.FIRST_WOOD_BREAK, serverPlayer.getUuid())) {
					PlayerEventsTracker.trackEventWithPayload(EventIds.FIRST_WOOD_BREAK, serverPlayer);
				}
			}
		});
	}
}