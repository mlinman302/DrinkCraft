package com.minman.drinkcraft.client;

import com.minman.drinkcraft.DrinkCraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HudImageManager {

    private static final Identifier COORS_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/coors.png");
    private static final Identifier IPA_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/ipa.png");
    private static final Identifier RBOW_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/rainbow.png");
    private static final Identifier SOYJAK_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/soyjak.png");
    private static final Identifier PINT_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/pint.png");
    private static final Identifier GUY_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/guy.png");


    private static final List<Identifier> IMAGES = new ArrayList<>();
    private static final Random myRandom = new Random();
    private static final List<OnScreenImage> ACTIVE_IMAGE = new ArrayList<>(1);

    private static final int MAX_IMAGES = 1;
    private static final double FADE_IN_TIME = 0.1;
    private static final double DISPLAY_TIME = 2.0;
    private static final double FADE_OUT_TIME = 2.0;



    public static void registerImages(){
        IMAGES.add(COORS_IMG);
        IMAGES.add(IPA_IMG);
        IMAGES.add(RBOW_IMG);
        IMAGES.add(SOYJAK_IMG);
        IMAGES.add(PINT_IMG);
        IMAGES.add(GUY_IMG);
    }

    public static void addImage(){
        OnScreenImage osImg = new OnScreenImage(
                IMAGES.get(myRandom.nextInt(IMAGES.size())),
                Util.getMeasuringTimeMs() / 1000.0,
                DISPLAY_TIME,
                FADE_IN_TIME,
                FADE_OUT_TIME);

        ACTIVE_IMAGE.addFirst(osImg);

    }

    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        // remove image if it is expired
        ACTIVE_IMAGE.removeIf(OnScreenImage::isExpired);

        // if no image
        if (ACTIVE_IMAGE.isEmpty()) {
            return;
        }

        int screenHeight = drawContext.getScaledWindowHeight();
        int screenWidth = drawContext.getScaledWindowWidth();


        ACTIVE_IMAGE.getFirst().renderImage(drawContext, screenWidth, screenHeight);
    }

    public static void renderOverScreen(){

    }
}
