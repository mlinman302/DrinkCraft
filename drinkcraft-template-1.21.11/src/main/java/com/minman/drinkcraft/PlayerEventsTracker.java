package com.minman.drinkcraft;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.*;


public class PlayerEventsTracker {
    // this holds all player data
    private static final PlayersEventsState playersState = new PlayersEventsState();


    // update player states
    public static void updatePlayersState(PlayersEventsState state){
        playersState.updateState(state);
    }

    private static PlayerEvents getPlayerEvents(UUID id){
        return playersState.getPlayersData().get(id);
    };

    public static PlayersEventsState getPlayersState(){
        return playersState;
    }


    // registers a new player if they aren't registered on the session yet
    public static void registerPlayer(ServerPlayerEntity player){
        playersState.getPlayersData().putIfAbsent(player.getUuid(), new PlayerEvents(player.getStringifiedName()));
    }

    // tracks an event to a specific player
    public static PlayerEvents trackEvent(String id, UUID uuid){
        DrinkEvent event = DrinkEventRegistry.getEvent(id);

            if (event == null) {
                throw new RuntimeException("Drink event: " + id + " not found in DrinkEventRegistry.");
            }

            PlayerEvents playerEvents = getPlayerEvents(uuid);

            int currentCount = playerEvents.getEventCounts().getOrDefault(id, 0);
            playerEvents.getEventCounts().put(id, currentCount + 1);
            playerEvents.addSips(event.sips());

            playersState.markDirty();
            return playerEvents;
    };


    public static void trackEventWithPayload(String id, ServerPlayerEntity player){
        PlayerEvents pEvents = trackEvent(id, player.getUuid());
        notifyClient(player, pEvents, DrinkEventRegistry.getEvent(id));
    }

    public static void trackEventForAllWithPayload(String id, ServerPlayerEntity player){
        trackEventForAll(id);
        MinecraftServer server = player.getEntityWorld().getServer();

        playersState.getPlayersData().forEach((uuid, pEvents) -> {
            assert server != null;
            notifyClient(Objects.requireNonNull(server.getPlayerManager().getPlayer(uuid)), pEvents, DrinkEventRegistry.getEvent(id));
        });
    }


//    public static Text trackEventWithPlayerMessage(String id, UUID uuid){
//        PlayerEventsTracker tracker = trackEvent(id, uuid);
//        return Text.literal(DrinkEventRegistry.getEvent(id).displayName()
//                + ". " + tracker.name + " Takes " + DrinkEventRegistry.getEvent(id).sips() + " Sips");
//    };

    public static void trackEventForAll(String id){
        DrinkEvent event = DrinkEventRegistry.getEvent(id);

        if (event == null) {
            throw new RuntimeException("Drink event not found in registry.");
        }

        playersState.getPlayersData().forEach((uuid, pEvents) -> {
            if (pEvents.shouldTrack(id)) {
                trackEvent(id, uuid);
            }
        });

    };

//    public static Text trackEventForAllWithPlayerMessage(String id){
//        trackEventForAll(id);
//        return Text.literal(DrinkEventRegistry.getEvent(id).displayName()
//                + ". All Players Take " + DrinkEventRegistry.getEvent(id).sips() + " Sips");
//
//    };

    public static boolean shouldTrack(String id, UUID uuid){
        PlayerEvents pEvents = getPlayerEvents(uuid); // get specific playerEvents
        return pEvents.shouldTrack(id); // return if it should track
    };

    public static PlayerEvents trackAdvancementEvent(String id, ServerPlayerEntity player) {
        PlayerEvents pEvents = getPlayerEvents(player.getUuid());
        pEvents.getAdvancementEvents().putIfAbsent(id, 2);
        pEvents.addSips(DrinkEventRegistry.getEvent(EventIds.ALL_ADVANCEMENTS).sips());
        playersState.markDirty();
        return pEvents;
    }

    public static void trackAdvancementWithPayload(AdvancementEntry advancement, ServerPlayerEntity player){
        String advId = advancement.id().toString();
        PlayerEvents pEvents = trackAdvancementEvent(advId, player);
        DrinkEvent basicAdvancementEvent = DrinkEventRegistry.getEvent(EventIds.ALL_ADVANCEMENTS);

        AdvancementDisplay display = advancement.value().display().orElse(null);

        String displayName;

        if (display != null) {
            displayName = display.getTitle().getString();
        }else{
            displayName = advId;
        }

        notifyClient(player, pEvents, new DrinkEvent(
                advId,
                displayName,
                basicAdvancementEvent.maxOccurrences(),
                basicAdvancementEvent.sips(),
                basicAdvancementEvent.forAll()
        ));

    }

//    public static Text trackAdvancementEventWithPlayerMessage(String id, UUID uuid){
//        PlayerEventsTracker tracker = trackAdvancementEvent(id, uuid);
//        return Text.literal(id + tracker.name + " Take " +
//                DrinkEventRegistry.getEvent(EventIds.ALL_ADVANCEMENTS).sips() + " Sips");
//    }

    public static boolean shouldTrackAdvancement(AdvancementEntry advancement, UUID uuid){
        String advId = advancement.id().toString();
        String[] splitId = advId.split("[:/]");
        return trackableAdvancement(splitId) && playerShouldTrackAdvancement(advId, uuid);
    }



    private static boolean trackableAdvancement(String[] splitAdvancementId){
        String branchName = splitAdvancementId[1];
        return splitAdvancementId[0].equals("minecraft") &&
                (branchName.equals("story") || branchName.equals("nether") || branchName.equals("end"));
    }

    private static boolean playerShouldTrackAdvancement(String advId, UUID uuid){
        PlayerEvents pEvents = getPlayerEvents(uuid);
        return pEvents.shouldTrackAdvancement(advId);

    }

    private static void notifyClient(ServerPlayerEntity player, PlayerEvents pEvents, DrinkEvent event){
        DrinkEventPayload payload = new DrinkEventPayload(
                event.sips(),
                pEvents.getTotalSips(),
                event.displayName(),
                event.id()
        );
        ServerPlayNetworking.send(player, payload);

    }


}
