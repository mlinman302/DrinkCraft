package com.minman.drinkcraft;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData extends PersistentState {

    // string is UUID string
    private Map<UUID, PlayerEventTracker> playersData = new HashMap<>();

    public PlayerData(){
        super();
    }

    public PlayerData(Map<UUID, PlayerEventTracker> map){
        this.playersData = map;
    }

    public void update ()

    public static final Codec<PlayerData> CODEC  =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.unboundedMap(Uuids.CODEC, PlayerEventTracker.CODEC)
                            .fieldOf("playersData")
                            .forGetter(PlayerData::getPlayersData)
            ).apply(instance, PlayerData::new));

    public Map<UUID, PlayerEventTracker> getPlayersData() {
        return this.playersData;
    }


    private static final PersistentStateType<PlayerData> type = new PersistentStateType<>(
            (String) DrinkCraft.MOD_ID,
            PlayerData::new,
            CODEC,
            null
    );

    public static PlayerData getServerState(MinecraftServer server){
        ServerWorld serverWorld = server.getWorld(World.OVERWORLD);
        assert serverWorld != null;
        PlayerData state = serverWorld.getPersistentStateManager().getOrCreate(type);

        state.markDirty();

        return state;
    }


}
