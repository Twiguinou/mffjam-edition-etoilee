package com.twihick.bunlyu.client.renderer.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.twihick.bunlyu.common.lib.function.LazyConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class GLFunctions extends RenderSystem {

    public static void adjustProjection() {
        ActiveRenderInfo info = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        rotateFloat(info.getPitch(), Vector3f.XP);
        rotateFloat(info.getYaw()+180.0F, Vector3f.YP);
        vec3dTranslate(info.getProjectedView().inverse());
    }

    public static void rotateFloat(float angle, Vector3f axis) {
        GL11.glRotatef(angle, axis.getX(), axis.getY(), axis.getZ());
    }

    public static void enableLightmap() {
        Minecraft.getInstance().gameRenderer.getLightTexture().enableLightmap();
    }

    public static void disableLightmap() {
        Minecraft.getInstance().gameRenderer.getLightTexture().disableLightmap();
    }

    public static void enableBlockTextureRendering() {
        TextureManager manager = Minecraft.getInstance().getTextureManager();
        manager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        manager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, true);
    }

    public static void shadeModelSmooth() {
        assertAll(() -> GL11.glShadeModel(GL11.GL_SMOOTH));
    }

    public static void shadeModelFlat() {
        assertAll(() -> GL11.glShadeModel(GL11.GL_FLAT));
    }

    public static void vec3dTranslate(Vec3d vec3d) {
        assertAll(() -> GL11.glTranslated(vec3d.x, vec3d.y, vec3d.z));
    }

    public static void pointSize(float size) {
        assertAll(() -> GL11.glPointSize(size));
    }

    public static void assertAll(LazyConsumer consumer) {
        assertThread(GLFunctions::isOnGameThread);
        assertThread(GLFunctions::isOnRenderThread);
        consumer.execute();
    }

}
