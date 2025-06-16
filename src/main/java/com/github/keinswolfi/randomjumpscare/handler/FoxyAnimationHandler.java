package com.github.keinswolfi.randomjumpscare.handler;

import com.github.keinswolfi.randomjumpscare.RandomJumpscare;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class FoxyAnimationHandler {
    private final Minecraft mc = Minecraft.getMinecraft();

    private boolean playing = false;
    private long startTime;
    private int frameDuration = 50; // ms per frame
    private int totalFrames = 14;

    public void startAnimation() {
        this.playing = true;
        this.startTime = System.currentTimeMillis();
    }

    public void render(RenderGameOverlayEvent.Post event) {
        if (!playing || event.type != RenderGameOverlayEvent.ElementType.ALL) return;

        long elapsed = System.currentTimeMillis() - startTime;
        int frameIndex = (int)(elapsed / frameDuration);

        if (frameIndex >= totalFrames) {
            playing = false;
            return;
        }

        ResourceLocation frame = new ResourceLocation(
                RandomJumpscare.MODID,
                "textures/gui/foxy_jumpscare/foxy-jumpscare-" + frameIndex + ".png"
        );

        mc.getTextureManager().bindTexture(frame);
        ScaledResolution res = new ScaledResolution(mc);

        int width = res.getScaledWidth();
        int height = res.getScaledHeight();

        // Draw full screen
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer wr = tessellator.getWorldRenderer();

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1F, 1F, 1F, 1F);

        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        wr.pos(0, height, -90).tex(0, 1).endVertex();
        wr.pos(width, height, -90).tex(1, 1).endVertex();
        wr.pos(width, 0, -90).tex(1, 0).endVertex();
        wr.pos(0, 0, -90).tex(0, 0).endVertex();
        tessellator.draw();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }
}
