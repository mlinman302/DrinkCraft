package com.minman.drinkcraft.client;

import net.minecraft.util.Util;

public abstract class OnScreenEvent {
    final double endTime;
    private final double duration;
    protected final double fadeInTime;
    private final double fadeOutTime;

    public OnScreenEvent(double startTime, double duration, double fadeInTime, double fadeOutTime, double waitTime){
        this.endTime = startTime + duration + waitTime;
        this.duration = duration;
        this.fadeInTime = fadeInTime;
        this.fadeOutTime = fadeOutTime;
    }


    public boolean isExpired(){
        return endTime - (Util.getMeasuringTimeMs() / 1000.0) <= 0;
    }

    public double getAlpha(double timeRemaining) {
        // wait for waitTime
        if(timeRemaining - duration > 0){
            return 0.0;
        }

        // Fade in
        double fadeInTimeRemaining = timeRemaining - (duration - fadeInTime);

        if(fadeInTimeRemaining >= 0.0){
            return 1.0 - (fadeInTimeRemaining / fadeInTime);
        }

        if(timeRemaining < fadeOutTime){
            return timeRemaining / fadeOutTime;
        }

        return 1.0;
    }
}
