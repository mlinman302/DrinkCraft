package com.minman.drinkcraft;

import com.minman.drinkcraft.client.ClientEventHandler;
import com.minman.drinkcraft.client.HudImageManager;
import com.minman.drinkcraft.client.HudNotificationManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class DrinkCraftModClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(DrinkCraft.MOD_ID);

    @Override
    public void onInitializeClient() {

        // setup event handler for when we receive a packet
        ClientEventHandler.register();

        // register images for rendering
        HudImageManager.registerImages();


        // attach image layer after Chat
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.CHAT,
                Identifier.of(DrinkCraft.MOD_ID, "before_chat"),
                HudImageManager::render);

        // attach notification layer on top
        HudElementRegistry.addFirst(
                Identifier.of(DrinkCraft.MOD_ID, "top_layer"),
                HudNotificationManager::render);
    }
}
