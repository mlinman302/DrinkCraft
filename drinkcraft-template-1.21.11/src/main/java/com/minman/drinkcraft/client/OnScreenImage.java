package com.minman.drinkcraft.client;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;

public class OnScreenImage extends OnScreenEvent implements Renderable{
    private final Identifier id;

    public OnScreenImage(Identifier id, double startTime, double duration, double fadeInTime, double fadeOutTime) {
        super(startTime, duration, fadeInTime, fadeOutTime, 0.0);
        this.id = id;
    }

    public void render(DrawContext drawContext){
        int screenWidth = drawContext.getScaledWindowWidth();
        int screenHeight = drawContext.getScaledWindowHeight();

        double timeRemaining = this.endTime - (Util.getMeasuringTimeMs() / 1000.0) ;
        double alpha = getAlpha(timeRemaining);



        drawContext.drawTexture(RenderPipelines.GUI_TEXTURED, this.id,
                (int) (screenWidth * 0.07), (int) (screenHeight * 0.07),
                0.0F, 0.0F,
                (int) (screenWidth * 0.8), (int) (screenHeight * 0.8),
                (int) (screenWidth * 0.8), (int) (screenHeight * 0.8),
                ColorHelper.getWhite((float)alpha));
    }
}


