package com.minman.drinkcraft;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DrinkEventPayload(
        int sips,
        int totalSips,
        String eventName,
        String eventId) implements CustomPayload{

    public static final CustomPayload.Id<DrinkEventPayload> ID =
            new CustomPayload.Id<>(Identifier.of(DrinkCraft.MOD_ID, "drink_event_update"));

    // Packet structure
    public static final PacketCodec<PacketByteBuf, DrinkEventPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.INTEGER, DrinkEventPayload::sips,
                    PacketCodecs.INTEGER, DrinkEventPayload::totalSips,
                    PacketCodecs.STRING, DrinkEventPayload::eventName,
                    PacketCodecs.STRING, DrinkEventPayload::eventId,
                    DrinkEventPayload::new
            );

    public static void registerPayload() {
        PayloadTypeRegistry.playS2C().register(
                DrinkEventPayload.ID,
                DrinkEventPayload.CODEC
        );
    }


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
