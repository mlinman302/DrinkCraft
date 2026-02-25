package com.minman.drinkcraft;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersEventsState extends PersistentState {

    private Map<UUID, PlayerEvents> playersData = new HashMap<>();

    public PlayersEventsState() {
        super();
    }

    public Map<UUID, PlayerEvents> getPlayersData() {
        return this.playersData;
    }


    // constructor for load/save
    public PlayersEventsState(Map<UUID, PlayerEvents> map) {
        this.playersData = new HashMap<>(map);
    }

    public void updateState(PlayersEventsState state){
        this.playersData = state.playersData;
    }



    public static PlayersEventsState getOrCreate(MinecraftServer server) {
        ServerWorld serverWorld = server.getWorld(World.OVERWORLD);  // get serverWorld (doesn't matter which World)
        assert serverWorld != null; // should always be non-null

        // get or create new state of PlayersEventData
        PlayersEventsState state = serverWorld.getPersistentStateManager().getOrCreate(type);

        state.markDirty();
        return state;
    }


    private static final Codec<PlayersEventsState> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.unboundedMap(Uuids.CODEC, PlayerEvents.CODEC)
                            .fieldOf("playersData")
                            .forGetter(PlayersEventsState::getPlayersData)
            ).apply(instance, PlayersEventsState::new));


    private static final PersistentStateType<PlayersEventsState> type = new PersistentStateType<>(
            DrinkCraft.MOD_ID,
            PlayersEventsState::new,
            CODEC,
            null
    );

    @Override
    public String toString(){
        return Arrays.toString(playersData.entrySet().toArray());
    }

}
