package me.frogdog.frogclient.module.modules.render;

import me.frogdog.frogclient.event.Event;

import me.frogdog.frogclient.Frog;
import me.frogdog.frogclient.event.Listener;
import me.frogdog.frogclient.event.events.RenderEvent;
import me.frogdog.frogclient.module.ModuleType;
import me.frogdog.frogclient.module.ToggleableModule;
import me.frogdog.frogclient.properties.NumberProperty;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;

public final class Tracers extends ToggleableModule {
	private final NumberProperty<Float> width = new NumberProperty<Float>(1f, 0.5f, 64f, "Width");
	private final NumberProperty<Integer> red = new NumberProperty<Integer>(0, 0, 255, "Red");
	private final NumberProperty<Integer> green = new NumberProperty<Integer>(255, 0, 255, "Green");
	private final NumberProperty<Integer> blue = new NumberProperty<Integer>(0, 0, 255, "Blue");
	private final NumberProperty<Integer> alpha = new NumberProperty<Integer>(100, 0, 100, "Opacity");

	public Tracers() {
		super("Tracers", new String[] {"tracers", "Tracers"}, ModuleType.RENDER);
		this.offerProperties(this.width, this.red, this.green, this.blue, this.alpha, this.keybind);
        this.listeners.add(new Listener<RenderEvent>("render_listener"){

            @Override
            public void call(RenderEvent event) {
            	for (Entity entity : Frog.getInstance().mc.world.loadedEntityList) {
            		drawLine(mc.player.posX - mc.getRenderManager().viewerPosX, mc.player.posY - mc.getRenderManager().viewerPosY, mc.player.posZ - mc.getRenderManager().viewerPosZ , entity.posX - mc.getRenderManager().viewerPosX, entity.posY - mc.getRenderManager().viewerPosY, entity.posZ - mc.getRenderManager().viewerPosZ, 1);
            	}
            }
        });
	}
	
	public void drawLine(double x, double y, double z, double x1, double y1, double z1, float width) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, z).color(0, 255, 0, 100).endVertex();
        bufferbuilder.pos(x1, y1, z1).color(0, 255, 0, 100).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
	}
	
}
