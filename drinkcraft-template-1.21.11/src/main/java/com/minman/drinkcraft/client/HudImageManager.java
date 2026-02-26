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
    private static final Identifier CALL_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/call.png");
    private static final Identifier DOG_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/dog.png");
    private static final Identifier GEM_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/gem.png");
    private static final Identifier OHTANI_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/ohtani.png");
    private static final Identifier SLIDER_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/slider.png");
    private static final Identifier TRUMP_IMG = Identifier.of(DrinkCraft.MOD_ID, "textures/gui/trump.png");
    

    private static final List<Identifier> IMAGES = new ArrayList<>(12);
    private static final Random myRandom = new Random();
    private static final List<OnScreenImage> ACTIVE_IMAGE = new ArrayList<>(1);

    public static void register(){
        IMAGES.add(COORS_IMG);
        IMAGES.add(IPA_IMG);
        IMAGES.add(RBOW_IMG);
        IMAGES.add(SOYJAK_IMG);
        IMAGES.add(PINT_IMG);
        IMAGES.add(GUY_IMG);
        IMAGES.add(CALL_IMG);
        IMAGES.add(DOG_IMG);
        IMAGES.add(GEM_IMG);
        IMAGES.add(OHTANI_IMG);
        IMAGES.add(SLIDER_IMG);
        IMAGES.add(TRUMP_IMG);
    }

    public static void addImage(){
        OnScreenImage osImg = new OnScreenImage(
                IMAGES.get(myRandom.nextInt(IMAGES.size())),
                Util.getMeasuringTimeMs() / 1000.0,
                ClientTimings.IMAGE_DURATION,
                ClientTimings.FAST_FADE_TIME,
                ClientTimings.SLOW_FADE_TIME);

        ACTIVE_IMAGE.addFirst(osImg);

    }

    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        // remove image if it is expired
        ACTIVE_IMAGE.removeIf(OnScreenImage::isExpired);

        // if no image
        if (ACTIVE_IMAGE.isEmpty()) {
            return;
        }

        ACTIVE_IMAGE.getFirst().render(drawContext);
    }

}
