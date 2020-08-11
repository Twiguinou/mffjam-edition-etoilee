package com.twihick.bunlyu.client.renderer.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class SpecialDrawer {

    public static void drawPoint(Vec3d point, float r, float g, float b, float a, float size) {
        GLFunctions.pushMatrix();
        GLFunctions.adjustProjection();
        GLFunctions.enableBlend();
        GLFunctions.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GLFunctions.disableTexture();
        GLFunctions.enableAlphaTest();
        GLFunctions.defaultAlphaFunc();
        GLFunctions.enableDepthTest();
        Minecraft mc = Minecraft.getInstance();
        GLFunctions.pointSize(Math.max(size, mc.getMainWindow().getFramebufferWidth()/1920.0F*size));
        boolean flag = Minecraft.isFancyGraphicsEnabled();
        if(flag)
            mc.worldRenderer.getEntityOutlineFramebuffer().bindFramebuffer(false);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
        builder.pos(point.x, point.y, point.z).color(r, g, b, a).endVertex();
        tessellator.draw();
        if(flag)
            mc.getFramebuffer().bindFramebuffer(false);
        GLFunctions.pointSize(1.0F);
        GLFunctions.disableDepthTest();
        GLFunctions.disableAlphaTest();
        GLFunctions.enableTexture();
        GLFunctions.disableBlend();
        GLFunctions.popMatrix();
    }

}
