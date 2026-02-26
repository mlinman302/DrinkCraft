package com.minman.drinkcraft.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class HudTextManager {

    public static final List<OnScreenText> DISPLAY_TEXT = new ArrayList<>();

    private static final int MAX_NOTIFICATIONS = 1;
    private static final double DISPLAY_TIME = Double.POSITIVE_INFINITY; // seconds
    private static final double FADE_TIME = 1.0; // seconds
    private static final double WAIT_TIME = 8.0; // seconds


    public static void addText(int sips){
        OnScreenText osText = new OnScreenText(
                Util.getMeasuringTimeMs(),
                DISPLAY_TIME,
                FADE_TIME,
                WAIT_TIME,
                "Test Message"
                );
        DISPLAY_TEXT.add(osText);

    }

    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        // remove text if it is expired
        DISPLAY_TEXT.removeIf(OnScreenText::isExpired);

        // if no text
        if (DISPLAY_TEXT.isEmpty()) {
            return;
        }

        int screenHeight = drawContext.getScaledWindowHeight();
        int screenWidth = drawContext.getScaledWindowWidth();

//
//        DISPLAY_TEXT.getFirst().renderText(drawContext);
    }
}
