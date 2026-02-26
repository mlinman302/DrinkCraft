package com.minman.drinkcraft.client;

import com.minman.drinkcraft.DrinkCraft;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class HudNotificationManager {

    private static final List<OnScreenNotification> ACTIVE_NOTIFICATIONS = new ArrayList<>();
    private static final int MAX_NOTIFICATIONS = 3;

    public static final Identifier TOAST_BG = Identifier.of("minecraft", "textures/gui/sprites/toast/advancement.png");
    public static final Identifier BEER_SPRITE = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/sprites/beer.png");



    public static void addNotification(String eventName, int sips, int totalSips) {
        // Remove the oldest if at max
        if (ACTIVE_NOTIFICATIONS.size() >= MAX_NOTIFICATIONS) {
            ACTIVE_NOTIFICATIONS.removeFirst();
        }

        ACTIVE_NOTIFICATIONS.add(new OnScreenNotification(
                eventName,
                sips,
                totalSips,
                Util.getMeasuringTimeMs() / 1000.0,
                ClientTimings.NOTIF_DURATION,
                ClientTimings.FAST_FADE_TIME,
                ClientTimings.FAST_FADE_TIME,
                ClientTimings.IMAGE_DURATION,
                ACTIVE_NOTIFICATIONS.size()));
    };


    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        // remove old notifications
        ACTIVE_NOTIFICATIONS.removeIf(OnScreenNotification::isExpired);

        // if no notifications
        if (ACTIVE_NOTIFICATIONS.isEmpty()){
            return;
        }

        // update position of remaining notifications
        for(int i = 0; i < ACTIVE_NOTIFICATIONS.size(); i++){
            ACTIVE_NOTIFICATIONS.get(i).setPosition(i);
        }

        // draw each notification
        ACTIVE_NOTIFICATIONS.forEach(notification -> {
                notification.render(drawContext);
        });

    }

}