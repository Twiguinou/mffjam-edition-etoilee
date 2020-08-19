package com.twihick.bunlyu.client.renderer.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class SpecialDrawer {

    public static void drawModel(IBakedModel model, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        matrix.push();
        matrix.translate(-0.5D, 0.0D, -0.5D);
        IVertexBuilder builder = buffer.getBuffer(RenderType.getSolid());
        Random rand = new Random();
        rand.setSeed(42L);
        for(Direction direction : Direction.values()) {
            for(BakedQuad quad : model.getQuads(null, direction, rand)) {
                float[] rgba = getColorRGBA(quad.getTintIndex());
                builder.addVertexData(matrix.getLast(), quad, rgba[0], rgba[1], rgba[2], rgba[3], light, overlay);
            }
            rand.setSeed(42L);
        }
        for(BakedQuad quad : model.getQuads(null, null, rand)) {
            float[] rgba = getColorRGBA(quad.getTintIndex());
            builder.addVertexData(matrix.getLast(), quad, rgba[0], rgba[1], rgba[2], rgba[3], light, overlay);
        }
        matrix.pop();
    }

    public static void drawSphere(Vec3d center, float r, float g, float b, float a, double radius) {
        GLFunctions.pushMatrix();
        GLFunctions.adjustProjection();
        GLFunctions.vec3dTranslate(center);
        GLFunctions.enableBlend();
        GLFunctions.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GLFunctions.disableTexture();
        GLFunctions.enableAlphaTest();
        GLFunctions.defaultAlphaFunc();
        GLFunctions.enableDepthTest();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        double precision = 20.0D;
        for(double i = 0.0D; i < Math.PI; i += Math.PI/precision) {
            builder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
            for(double j = 0.0D; j < Math.pow(Math.PI, 2); j += Math.PI/precision) {
                double x = radius * Math.cos(j) * Math.sin(i);
                double y = radius * Math.sin(j) * Math.sin(i);
                double z = radius * Math.cos(i);
                builder.pos(x, y, z).color(r, g, b, a).endVertex();
                double sin = Math.sin(i+Math.PI/precision);
                x = radius * Math.cos(j) * sin;
                y = radius * Math.sin(j) * sin;
                z = radius * Math.cos(i+Math.PI/precision);
                builder.pos(x, y, z).color(r, g, b, a).endVertex();
            }
            tessellator.draw();
        }
        GLFunctions.disableDepthTest();
        GLFunctions.disableAlphaTest();
        GLFunctions.enableTexture();
        GLFunctions.disableBlend();
        GLFunctions.popMatrix();
    }

    public static float[] getColorRGBA(int color) {
        return new float[] {
                (color >> 16 & 255) / 255.0F,
                (color >> 8 & 255) / 255.0F,
                (color & 255) / 255.0F,
                (color >> 24 & 255) / 255.0F
        };
    }

}
