package com.github.keinswolfi.randomjumpscare;

import com.github.keinswolfi.randomjumpscare.command.AnimationCommand;
import com.github.keinswolfi.randomjumpscare.event.SecondPassedEvent;
import com.github.keinswolfi.randomjumpscare.handler.FoxyAnimationHandler;
import com.github.keinswolfi.randomjumpscare.handler.TickEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod(modid = "randomjumpscare", useMetadata=true)
public class RandomJumpscare {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static final String MODID = "randomjumpscare";

    private final Random random = new Random();
    private final FoxyAnimationHandler animationHandler = new FoxyAnimationHandler();

    public static boolean trigger = false;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Dirt: " + Blocks.dirt.getUnlocalizedName());
		// Below is a demonstration of an access-transformed class access.
        System.out.println("Color State: " + new GlStateManager.Color());

        MinecraftForge.EVENT_BUS.register(this);

        new TickEventHandler();

        ClientCommandHandler.instance.registerCommand(new AnimationCommand());
    }

    @SubscribeEvent
    public void onSecondPassed(SecondPassedEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (random.nextInt(10_000) == 0 || trigger) {
            // IChatComponent chatComponent = new ChatComponentText("BLARGHHH");
            // mc.thePlayer.addChatMessage(chatComponent);
            animationHandler.startAnimation();

            mc.getSoundHandler().playSound(
                    PositionedSoundRecord.create(
                            new ResourceLocation(MODID, "jumpscare.foxy")
                    )
            );

            trigger = false;
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        animationHandler.render(event);
    }
}
