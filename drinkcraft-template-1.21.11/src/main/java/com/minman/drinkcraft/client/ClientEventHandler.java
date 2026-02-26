package com.minman.drinkcraft.client;

import com.minman.drinkcraft.DrinkEventPayload;
import com.minman.drinkcraft.sound.DrinkSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

public class ClientEventHandler {

    public static void register(){
        ClientPlayNetworking.registerGlobalReceiver(
                DrinkEventPayload.ID,
                ((payload, context) -> {
                    context.client().execute(() ->
                            onPointsUpdate(payload, context.client()));
                })
        );
    }

    private static void onPointsUpdate(DrinkEventPayload payload, MinecraftClient client) {
        // unpack payload
        int sips = payload.sips();
        int totalSips = payload.totalSips();
        String eventName = payload.eventName();
        String eventId = payload.eventId();

        // play sound

        if (client.player != null){
            client.player.playSound(
                    DrinkSounds.BEER_DRINK,
                    1f,
                    1f
            );
        }


        // Show HUD notification
        HudNotificationManager.addNotification(
                eventName,
                sips,
                totalSips
        );

        // Show Image
        HudImageManager.addImage();

        HudTextManager.addText(totalSips);


    }
}
