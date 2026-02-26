package com.minman.drinkcraft.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;

public class OnScreenNotification extends OnScreenEvent{
    private final String eventName;
    private final int sips;
    private final int totalSips;
    private int position;

    public OnScreenNotification(String eventName, int sips, int totalSips, double startTime, double duration, double fadeInTime, double fadeOutTime, double waitTime, int position) {
        super(startTime, duration, fadeInTime, fadeOutTime, waitTime);
        this.eventName = eventName;
        this.sips = sips;
        this.totalSips = totalSips;
    }

    public void setPosition(int pos){
        position = pos;
    }

    public int getTotalSips(){
        return this.totalSips;
    }

    public void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();

        double timeRemaining = this.endTime - (Util.getMeasuringTimeMs() / 1000.0) ;

        double alpha = getAlpha(timeRemaining);
        float alphaF = (float) alpha;

        String pointsText = (sips > 0 ? "§a+" : "§c") + sips + " sips";
        String totalText = "§7Total: " + totalSips;
        String nameText = "§6" + eventName;

        int padding = 5;
        int x = 0;
        int y = 30 * position;


        context.drawTexture(RenderPipelines.GUI_TEXTURED, HudNotificationManager.TOAST_BG,
                x, y,
                0.0F, 0.0F,
                160, 32,
                160, 32,
                ColorHelper.getWhite(alphaF));


        // Draw sprite
        context.drawTexture(RenderPipelines.GUI_TEXTURED, HudNotificationManager.BEER_SPRITE,
                x + 6, y + 6,
                0.0F, 0.0F,
                20, 20,
                20, 20,
                ColorHelper.getWhite(alphaF));


        // Draw text
        context.drawText(client.textRenderer, nameText, x + 30, y + 6, ColorHelper.withAlpha(alphaF, 0xFFFFFF), false);
        context.drawText(client.textRenderer, pointsText, x + 30, y + 16, ColorHelper.withAlpha(alphaF, 0xFFFFFF), false);
        context.drawText(client.textRenderer, totalText, x + 100, y + 16, ColorHelper.withAlpha(alphaF, 0xAAAAAA), false);
    }
}
