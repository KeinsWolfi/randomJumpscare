package com.github.keinswolfi.randomjumpscare.handler;

import com.github.keinswolfi.randomjumpscare.event.SecondPassedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickEventHandler {
    private int tickCounter = 0;

    public TickEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        tickCounter++;

        if (tickCounter >= 20) {
            MinecraftForge.EVENT_BUS.post(new SecondPassedEvent());
            tickCounter = 0;
        }
    }
}
