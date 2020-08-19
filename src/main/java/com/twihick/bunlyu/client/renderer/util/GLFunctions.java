package com.twihick.bunlyu.client.renderer.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.twihick.bunlyu.common.lib.function.LazyConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Vector3f;
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

    public static void fullBright() {
        assertAll(() -> GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F));
    }

    public static void rotateFloat(float angle, Vector3f axis) {
        GL11.glRotatef(angle, axis.getX(), axis.getY(), axis.getZ());
    }

    public static void vec3dTranslate(Vec3d vec3d) {
        assertAll(() -> GL11.glTranslated(vec3d.x, vec3d.y, vec3d.z));
    }

    public static void assertAll(LazyConsumer consumer) {
        assertThread(GLFunctions::isOnGameThread);
        assertThread(GLFunctions::isOnRenderThread);
        consumer.execute();
    }

}
