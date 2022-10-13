package me.frogdog.hecks.module.modules.render;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.module.ModuleType;
import me.frogdog.hecks.module.ToggleableModule;
import me.frogdog.hecks.property.NumberProperty;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public final class ESP extends ToggleableModule {
    private final NumberProperty<Float> width = new NumberProperty<Float>(1f, 0.5f, 64f, "Width");
    private final NumberProperty<Integer> red = new NumberProperty<Integer>(0, 0, 255, "Red");
    private final NumberProperty<Integer> green = new NumberProperty<Integer>(255, 0, 255, "Green");
    private final NumberProperty<Integer> blue = new NumberProperty<Integer>(0, 0, 255, "Blue");
    private final NumberProperty<Integer> alpha = new NumberProperty<Integer>(100, 0, 100, "Opacity");

    public ESP() {
        super("ESP", new String[] {"esp", "ESP"}, "Allows you to see entities through walls", ModuleType.RENDER);
        this.offerProperties(this.width, this.red, this.green, this.blue, this.alpha, this.keybind);
    }

    @Override
    public void render(RenderWorldLastEvent event) {
        if (!Hecks.mc.world.getLoadedEntityList().isEmpty()) {
            ShaderGroup shader = Hecks.mc.renderGlobal.entityOutlineShader;
        }

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer && entity != mc.player) {
                //drawOutline(entity.posX, entity.posY, entity.posZ, width.getValue(), entity.width, entity.height, red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());
                drawOutline(entity);
            }

            if (entity instanceof EntityMob) {
                //drawOutline(entity.posX, entity.posY, entity.posZ, width.getValue(), entity.width , entity.height, red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());
                drawOutline(entity);
            }

            if (entity instanceof EntityAnimal) {
                //drawOutline(entity.posX, entity.posY, entity.posZ, width.getValue(), entity.width , entity.height, red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());
                drawOutline(entity);
            }
        }
    }

    private void drawOutline(Entity entity) {
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();

        Boolean renderOutlines = mc.renderManager.renderOutlines;
        Render renderer = Hecks.mc.renderManager.getEntityRenderObject(entity);

        renderer.setRenderOutlines(true);
        renderer.doRender(entity, entity.posX, entity.posY, entity.posZ, entity.rotationYaw, Hecks.mc.getRenderPartialTicks());
        renderer.setRenderOutlines(renderOutlines);

        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
    }

    /*
    public void drawOutline(double posX, double posY, double posZ, float lineWidth, float width, float height, int red, int green, int blue, int alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(green, blue, alpha, alpha);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(lineWidth);
        double x = posX - mc.getRenderManager().viewerPosX - 0.3;
        double y = posY - mc.getRenderManager().viewerPosY;
        double z = posZ - mc.getRenderManager().viewerPosZ - 0.3;
        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + width, y + height, z + width);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
     */

}
