package com.minman.drinkcraft.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public interface Renderable {

    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) { };
}
