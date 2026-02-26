package com.minman.drinkcraft.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class HudTextManager {

    public static final List<OnScreenText> DISPLAY_TEXT = new ArrayList<>(1);
    private static final double FADE_TIME = 1.0; // seconds
    private static final double WAIT_TIME = 5.0; // seconds


    public static void addText(int sips) {
        OnScreenText osText = new OnScreenText(
                Util.getMeasuringTimeMs() / 1000.0,
                ClientTimings.SLOW_FADE_TIME,
                ClientTimings.IMAGE_DURATION + ClientTimings.NOTIF_DURATION,
                String.format("%d Sips", sips)
        );
        // only one element in the list
        DISPLAY_TEXT.addFirst(osText);

    }

    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        // no need to remove text as it is only one in first index

        // if no text
        if (DISPLAY_TEXT.isEmpty()) {
            return;
        }

        DISPLAY_TEXT.getFirst().render(drawContext);
    }
}
