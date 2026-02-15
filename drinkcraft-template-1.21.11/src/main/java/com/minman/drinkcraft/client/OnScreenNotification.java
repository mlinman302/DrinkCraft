package com.minman.drinkcraft.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Util;

public class OnScreenNotification extends OnScreenEvent{
    private final String eventName;
    private final int sips;
    private final int totalSips;

    public OnScreenNotification(String eventName, int sips, int totalSips, double startTime, double duration, double fadeInTime, double fadeOutTime) {
        super(startTime, duration, fadeInTime, fadeOutTime);
        this.eventName = eventName;
        this.sips = sips;
        this.totalSips = totalSips;
    }

    public void render(DrawContext context, int screenWidth, int y) {
        MinecraftClient client = MinecraftClient.getInstance();

        double timeRemaining = this.endTime - (Util.getMeasuringTimeMs() / 1000.0) ;

        double alpha = getAlpha(timeRemaining);
        int alphaInt = (int) (alpha * 255);

        String pointsText = (sips > 0 ? "§a+" : "§c") + sips + " sips";
        String totalText = "§7Total: " + totalSips;
        String nameText = "§6" + eventName;

        int x = screenWidth - 160;

        // Draw background
        context.fill(
                x - 5, y - 5,
                x + 155, y + 25,
                (alphaInt / 2) << 24  // Semi-transparent black
        );

        // Draw text
        context.drawText(client.textRenderer, nameText, x, y, 0xFFFFFF | (alphaInt << 24), false);
        context.drawText(client.textRenderer, pointsText, x, y + 10, 0xFFFFFF | (alphaInt << 24), false);
        context.drawText(client.textRenderer, totalText, x, y + 20, 0xAAAAAA | (alphaInt << 24), false);
    }
}
