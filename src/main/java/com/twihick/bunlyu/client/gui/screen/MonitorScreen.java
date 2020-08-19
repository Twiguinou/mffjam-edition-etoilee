package com.twihick.bunlyu.client.gui.screen;

import com.twihick.bunlyu.Main;
import com.twihick.bunlyu.client.renderer.util.GLFunctions;
import com.twihick.bunlyu.common.lib.text.EntryType;
import com.twihick.bunlyu.common.lib.text.TextComponentWriter;
import com.twihick.bunlyu.common.tileentities.MonitorTileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.Color;

@OnlyIn(Dist.CLIENT)
public class MonitorScreen extends Screen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(Main.ID, "textures/gui/monitor_screen_background.png");

    private final MonitorTileEntity monitor;
    private final int sizeX;
    private final int sizeY;
    private Button buttonRide;

    public MonitorScreen(MonitorTileEntity monitor) {
        super(TextComponentWriter.getTranslated(EntryType.GUI, "monitor_screen"));
        this.monitor = monitor;
        this.sizeX = 151;
        this.sizeY = 151;
    }

    private Pair<Integer, Integer> getScaledSize() {
        return Pair.of((this.width-this.sizeX)/2, (this.height-this.sizeY)/2);
    }

    private boolean isConnected() {
        return this.monitor.isConnected();
    }

    private double getScaleFactor() {
        return this.minecraft.getMainWindow().getGuiScaleFactor();
    }

    @Override
    protected void init() {
        Pair<Integer, Integer> pair = this.getScaledSize();
        this.buttonRide = this.addButton(new Button(pair.getLeft()+this.sizeX/2-40, (pair.getRight()+this.sizeY/2-10)+40, 80, 20, TextComponentWriter.getI18nFormat(EntryType.GUI, "button.ride"), (button) -> {
            this.minecraft.player.closeScreen();
        }));
        this.buttonRide.active = this.isConnected();
    }

    @Override
    public void tick() {
        this.buttonRide.active = this.isConnected();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        GLFunctions.fullBright();
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        Pair<Integer, Integer> pair = this.getScaledSize();
        this.blit(pair.getLeft(), pair.getRight(), 0, 0, this.sizeX, this.sizeY);
        if(!this.isConnected()) {
            String text = TextComponentWriter.getTranslated(EntryType.ALERT, "monitor.not_connected").getFormattedText();
            GLFunctions.scaled(0.5D, 0.5D, 0.5D);
            this.font.drawString(text, this.width-this.font.getStringWidth(text)/2, this.height, Color.WHITE.getRGB());
            return;
        }
        super.render(mouseX, mouseY, partialTicks);
    }

}
