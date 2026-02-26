package com.minman.drinkcraft.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;

public class OnScreenText extends OnScreenEvent implements Renderable{
    private final String message;
    private final double startTime;
    private final double waitTime;

    public OnScreenText(double startTime, double fadeInTime, double waitTime, String message) {
        super(startTime, Double.POSITIVE_INFINITY, fadeInTime, 0.0, waitTime);
        this.message = message;
        this.startTime = startTime;
        this.waitTime = waitTime;
    }

    public void render(DrawContext drawContext){
        MinecraftClient client = MinecraftClient.getInstance();
        double currentTime = Util.getMeasuringTimeMs() / 1000.0;
        float alphaF = (float) getAlpha(currentTime - startTime);

        drawContext.drawTextWithShadow(client.textRenderer, message, 10, 10, ColorHelper.withAlpha(alphaF, 0xFFFFFF));
    }

    @Override
    public double getAlpha(double timeSoFar){
        if(timeSoFar < waitTime){
            return 0.0;
        } else if (timeSoFar < fadeInTime + waitTime) {
            return (timeSoFar - waitTime) / fadeInTime;
        } else{
            return 1.0;
        }
    };

}
