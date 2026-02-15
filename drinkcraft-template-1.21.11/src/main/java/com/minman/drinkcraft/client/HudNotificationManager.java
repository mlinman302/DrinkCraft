package com.minman.drinkcraft.client;

import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Util;

public class HudNotificationManager implements Renderable {

    private static final List<OnScreenNotification> ACTIVE_NOTIFICATIONS = new ArrayList<>();
    private static final int MAX_NOTIFICATIONS = 3;
    private static final double DISPLAY_TIME = 4.0; // seconds
    private static final double FADE_TIME = 1.0; // seconds



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
                DISPLAY_TIME,
                FADE_TIME,
                FADE_TIME));
    };

    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        // remove old notifications
        ACTIVE_NOTIFICATIONS.removeIf(OnScreenNotification::isExpired);

        // if no notifications
        if (ACTIVE_NOTIFICATIONS.isEmpty()){
            return;
        }

        // if there are notifications then draw them
        int screenHeight = drawContext.getScaledWindowHeight();
        int screenWidth = drawContext.getScaledWindowWidth();

            int y = screenHeight / 2 - 40; // Start above center

            for (OnScreenNotification notification : ACTIVE_NOTIFICATIONS) {
                notification.renderNotification(drawContext, screenWidth, y);
                y -= 30; // Stack notifications upward
            }

    }

}