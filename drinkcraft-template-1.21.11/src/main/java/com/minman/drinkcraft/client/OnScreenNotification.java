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

    public OnScreenNotification(String eventName, int sips, int totalSips, double startTime, double duration, double fadeInTime, double fadeOutTime, double waitTime) {
        super(startTime, duration, fadeInTime, fadeOutTime, waitTime);
        this.eventName = eventName;
        this.sips = sips;
        this.totalSips = totalSips;
    }

    public void renderNotification(DrawContext context, int screenWidth, int y) {
        MinecraftClient client = MinecraftClient.getInstance();

        double timeRemaining = this.endTime - (Util.getMeasuringTimeMs() / 1000.0) ;

        double alpha = getAlpha(timeRemaining);
        float alphaF = (float) alpha;

        String pointsText = (sips > 0 ? "§a+" : "§c") + sips + " sips";
        String totalText = "§7Total: " + totalSips;
        String nameText = "§6" + eventName;

        int x = (int) (screenWidth * 0.05);


        context.drawTexture(RenderPipelines.GUI_TEXTURED, HudNotificationManager.TOAST_BG,
                x - 5, y - 5,
                0.0F, 0.0F,
                160, 32,
                160, 32,
                ColorHelper.getWhite(alphaF));


        // Draw sprite
        context.drawTexture(RenderPipelines.GUI_TEXTURED, HudNotificationManager.BEER_SPRITE,
                x, y,
                0.0F, 0.0F,
                20, 20,
                20, 20,
                ColorHelper.getWhite(alphaF));


        // Draw text
        context.drawText(client.textRenderer, nameText, x + 30, y, ColorHelper.withAlpha(alphaF, 0xFFFFFF), false);
        context.drawText(client.textRenderer, pointsText, x + 30, y + 10, ColorHelper.withAlpha(alphaF, 0xFFFFFF), false);
        context.drawText(client.textRenderer, totalText, x + 100, y + 10, ColorHelper.withAlpha(alphaF, 0xAAAAAA), false);
    }
}
