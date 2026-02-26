package com.minman.drinkcraft.client;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;

public class OnScreenText extends OnScreenEvent{
    private String message;

    public OnScreenText(double startTime, double duration, double fadeInTime, double waitTime, String message) {
        super(startTime, duration, fadeInTime, 0, waitTime);
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

//    public void renderText(DrawContext drawContext){
//        drawContext.drawTextWithShadow(RenderPipelines.GUI_TEXT, OrderedText.);
//    }
}
