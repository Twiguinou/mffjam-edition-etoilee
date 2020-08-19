package com.twihick.bunlyu.client.gui;

import com.twihick.bunlyu.client.gui.screen.MonitorScreen;
import com.twihick.bunlyu.common.tileentities.MonitorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiHelpers {

    public static boolean showMonitorScreen(World worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof MonitorTileEntity) {
            Minecraft.getInstance().displayGuiScreen(new MonitorScreen((MonitorTileEntity)te));
            return true;
        }
        return false;
    }

}
