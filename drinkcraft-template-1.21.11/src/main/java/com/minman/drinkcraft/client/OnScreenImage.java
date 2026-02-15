package com.minman.drinkcraft.client;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;

public class OnScreenImage extends OnScreenEvent {
    private final Identifier id;

    public OnScreenImage(Identifier id, double startTime, double duration, double fadeInTime, double fadeOutTime) {
        super(startTime, duration, fadeInTime, fadeOutTime);
        this.id = id;
    }

    public void renderImage(DrawContext drawContext, int screenWidth, int screenHeight){

        double timeRemaining = this.endTime - (Util.getMeasuringTimeMs() / 1000.0) ;
        double alpha = getAlpha(timeRemaining);



        drawContext.drawTexture(RenderPipelines.GUI_TEXTURED, this.id,
                (int) (screenWidth * 0.1), (int) (screenHeight * 0.1),
                0.0F, 0.0F,
                (int) (screenWidth * 0.8), (int) (screenHeight * 0.8),
                (int) (screenWidth * 0.8), (int) (screenHeight * 0.8),
                ColorHelper.getWhite((float)alpha));
    }
}


